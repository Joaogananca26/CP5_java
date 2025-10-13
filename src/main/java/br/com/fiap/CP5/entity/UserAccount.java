package br.com.fiap.CP5.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(name="UX_USERS_EMAIL", columnNames = "EMAIL_USUARIO"))
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserAccount {

    @Id
    @Column(name = "ID_USUARIO", length = 36, nullable = false)
    private String idUsuario;

    @Column(name = "EMAIL_USUARIO", length = 190, nullable = false)
    private String emailUsuario;

    @Column(name = "PASSWORD_HASH_USUARIO", length = 100, nullable = false)
    private String passwordHashUsuario;

    @Column(name = "NOME_COMPLETO_USUARIO", length = 120, nullable = false)
    private String nomeCompletoUsuario;

    @Column(name = "TELEFONE_USUARIO", length = 20)
    private String telefoneUsuario;

    @Column(name = "ROLE_USUARIO", length = 40, nullable = false)
    private String roleUsuario = "USER";

    @Column(name = "ENABLED_USUARIO", length = 1, nullable = false)
    private String enabledUsuario = "Y";

    @Column(name="CREATED_AT", nullable=false)
    private LocalDateTime createdAt = LocalDateTime.now();

}
