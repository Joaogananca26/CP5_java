package br.com.fiap.CP5.repository;

import br.com.fiap.CP5.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    Optional<UserAccount> findByEmailUsuario(String emailUsuario);
    boolean existsByEmailUsuario(String emailUsuario);
}