package br.com.fiap.CP5.controller;

import br.com.fiap.CP5.entity.Item;
import br.com.fiap.CP5.exception.ItemNaoEncontradoException;
import br.com.fiap.CP5.dto.request.ItemRequest;
import br.com.fiap.CP5.dto.response.ItemResponse;
import br.com.fiap.CP5.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/itens")
public class ItemControllerView {

    private final ItemService itemService;

    public ItemControllerView(ItemService itemService) {
        this.itemService = itemService;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public String listar(Model model,
                         @ModelAttribute("mensagem") String mensagem,
                         @ModelAttribute("erro") String erro) {

        List<ItemResponse> itens = itemService.listarItens();
        model.addAttribute("itens", itens);
        return "item/listar";
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/novo")
    public String formNovo(Model model) {
        model.addAttribute("item", new ItemRequest());
        model.addAttribute("title", "Cadastrar Item");
        model.addAttribute("modo", "criar");
        return "item/adicionar";
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public String criar(@Valid @ModelAttribute("item") ItemRequest request,
                        BindingResult result,
                        RedirectAttributes ra,
                        Model model) {
        if (result.hasErrors()) {
            model.addAttribute("title", "Cadastrar Item");
            model.addAttribute("modo", "criar");
            return "item/adicionar";
        }

        Item novo = Item.builder()
                .nomeItem(request.getNomeItem())
                .tipoItem(request.getTipoItem())
                .classificacaoItem(request.getClassificacaoItem())
                .tamanhoItem(request.getTamanhoItem())
                .precoItem(request.getPrecoItem())
                .build();

        Item salvo = itemService.createItem(novo);
        ra.addFlashAttribute("mensagem", "Item cadastrado com sucesso (ID: " + salvo.getIdItem() + ").");
        return "redirect:/itens";
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    public String detalhes(@PathVariable String id, Model model, RedirectAttributes ra) {
        try {
            Item item = itemService.obterItemPorId(id);
            model.addAttribute("item", item);
            return "item/editarItem";
        } catch (ItemNaoEncontradoException e) {
            ra.addFlashAttribute("erro", e.getMessage());
            return "redirect:/itens";
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/editar/{id}")
    public String formEditar(@PathVariable String id, Model model, RedirectAttributes ra) {
        try {
            Item item = itemService.obterItemPorId(id);
            ItemRequest form = new ItemRequest(
                    item.getNomeItem(),
                    item.getTipoItem(),
                    item.getClassificacaoItem(),
                    item.getTamanhoItem(),
                    item.getPrecoItem()
            );
            model.addAttribute("itemId", item.getIdItem());
            model.addAttribute("item", form);
            model.addAttribute("title", "Editar Item");
            model.addAttribute("modo", "editar");
            return "item/editarItem";
        } catch (ItemNaoEncontradoException e) {
            ra.addFlashAttribute("erro", e.getMessage());
            return "redirect:/itens";
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/{id}")
    public String atualizar(@PathVariable String id,
                            @Valid @ModelAttribute("item") ItemRequest request,
                            BindingResult result,
                            RedirectAttributes ra,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("itemId", id);
            model.addAttribute("title", "Editar Item");
            model.addAttribute("modo", "editar");
            return "item/editarItem";
        }
        try {
            Item atualizado = itemService.atualizarItem(id, request);
            ra.addFlashAttribute("mensagem", "Item atualizado com sucesso (ID: " + atualizado.getIdItem() + ").");
            return "redirect:/itens";
        } catch (ItemNaoEncontradoException e) {
            ra.addFlashAttribute("erro", e.getMessage());
            return "redirect:/itens";
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable String id, RedirectAttributes ra) {
        try {
            itemService.deletarItemPorId(id);
            ra.addFlashAttribute("sucesso", "Item excluído com sucesso");
        } catch (ItemNaoEncontradoException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/itens";
    }
}
