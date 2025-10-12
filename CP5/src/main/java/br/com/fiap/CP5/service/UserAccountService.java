package br.com.fiap.CP5.service;

import br.com.fiap.CP5.entity.UserAccount;
import br.com.fiap.CP5.repository.UserAccountRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserAccountService implements UserDetailsService {

    private final UserAccountRepository repo;
    private final PasswordEncoder encoder;

    public UserAccountService(UserAccountRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount u = repo.findByEmailUsuario(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        boolean enabled = "Y".equalsIgnoreCase(u.getEnabledUsuario());
        return new User(
                u.getEmailUsuario(),
                u.getPasswordHashUsuario(),
                "Y".equalsIgnoreCase(u.getEnabledUsuario()),
                true, true, true,
                List.of(new SimpleGrantedAuthority("ROLE_" + u.getRoleUsuario()))
        );
    }


    public UserAccount register(String nome, String email, String senha, String telefone) {
        String emailNorm = email.toLowerCase().trim();
        if (repo.existsByEmailUsuario(emailNorm)) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
        UserAccount u = new UserAccount();
        u.setIdUsuario(UUID.randomUUID().toString());
        u.setNomeCompletoUsuario(nome);
        u.setEmailUsuario(emailNorm);
        u.setPasswordHashUsuario(encoder.encode(senha));
        u.setTelefoneUsuario(telefone);
        u.setRoleUsuario("USER");
        u.setEnabledUsuario("Y");
        return repo.save(u);
    }
}