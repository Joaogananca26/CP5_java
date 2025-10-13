package br.com.fiap.CP5.controller;


import br.com.fiap.CP5.service.UserAccountService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
public class AuthController {

    private final UserAccountService userService;

    public AuthController(UserAccountService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error,
                        @RequestParam(value="logout", required=false) String logout,
                        @RequestParam(value="registered", required=false) String registered,
                        Model model) {
        if (error != null) model.addAttribute("erro", "Credenciais inválidas.");
        if (logout != null) model.addAttribute("mensagem", "Sessão encerrada.");
        if (registered != null) model.addAttribute("mensagem", "Cadastro realizado! Faça login.");
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerForm() { return "auth/register"; }

    @PostMapping("/register")
    public String register(@RequestParam @NotBlank String nomeCompletoUsuario,
                           @RequestParam @Email String emailUsuario,
                           @RequestParam @NotBlank String senhaUsuario,
                           @RequestParam(required = false) String telefoneUsuario,
                           Model model) {
        try {
            userService.register(nomeCompletoUsuario, emailUsuario, senhaUsuario, telefoneUsuario);
            return "redirect:/login?registered=true";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("erro", ex.getMessage());
            return "auth/register";
        }
    }
}