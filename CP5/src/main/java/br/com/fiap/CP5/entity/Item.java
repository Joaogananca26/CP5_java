package br.com.fiap.CP5.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "TB_ITEM")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Item {

    @Id
    @Column(name = "id_item")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idItem;

    @Column(name = "nome_item", nullable = false, length = 50)
    private String nomeItem;

    @Column(name = "tipo_item", nullable = false, length = 50)
    private String tipoItem;

    @Column(name = "classificacao_item", nullable = false, length = 50)
    private String classificacaoItem;

    @Column(name = "tamanho_item", nullable = false, length = 50)
    private double tamanhoItem;

    @Column(name = "preco_item", nullable = false, length = 50)
    private double precoItem;
}