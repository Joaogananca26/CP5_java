package br.com.fiap.CP5.service;

import br.com.fiap.CP5.ItemNaoEncontradoException;
import br.com.fiap.CP5.dto.request.ItemRequest;
import br.com.fiap.CP5.dto.response.ItemResponse;
import br.com.fiap.CP5.entity.Item;
import br.com.fiap.CP5.repository.ItemRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ItemService {

    private final ItemRepository repository;

    @Autowired
    public ItemService(ItemRepository repository){
        this.repository = repository;
    }


    public Item createItem(Item item) {
        return repository.save(item);
    }

    public List<ItemResponse> listarItens() {
        try {
            return repository.findAll()
                    .stream()
                    .map(item -> new ItemResponse(
                            item.getIdItem(),
                            item.getNomeItem(),
                            item.getTipoItem(),
                            item.getClassificacaoItem(),
                            item.getTamanhoItem(),
                            item.getPrecoItem()

                    ))
                    .toList();
        } catch (Exception e) {
            System.out.println("Houve um erro ao tentar resgatar todos os itens: ");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Item obterItemPorId(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ItemNaoEncontradoException("Item não encontrado com o ID: " + id));
    }

    public void deletarItemPorId(UUID id) {
        if (!repository.existsById(id)) {
            throw new ItemNaoEncontradoException("Item não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional
    public void atualizarItem(UUID id, ItemRequest itemAtualizado) {

        Item itemExistente = obterItemPorId(id);

        if (itemAtualizado.getNomeItem() != null)
            itemExistente.setNomeItem(itemAtualizado.getNomeItem());

        if (itemAtualizado.getTipoItem() != null)
            itemExistente.setTipoItem(itemAtualizado.getTipoItem());

        if (itemAtualizado.getClassificacaoItem() != null)
            itemExistente.setClassificacaoItem(itemAtualizado.getClassificacaoItem());

        if (itemAtualizado.getTamanhoItem() != 0)
            itemExistente.setTamanhoItem(itemAtualizado.getTamanhoItem());

        if (itemAtualizado.getPrecoItem() != 0)
            itemExistente.setPrecoItem(itemAtualizado.getPrecoItem());

        System.out.println("Item: " + itemExistente.getIdItem() + ", atualizado com sucesso para: "
                + itemExistente.getNomeItem() + " " + itemExistente.getTipoItem() + ", "
                + itemExistente.getClassificacaoItem() + ", " + itemExistente.getTamanhoItem() + ", "
                + itemExistente.getPrecoItem());
    }
}
