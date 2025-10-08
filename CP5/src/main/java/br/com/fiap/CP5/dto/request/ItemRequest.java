package br.com.fiap.CP5.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {

    @NotBlank(message = "O nome do item não pode ficar em branco")
    private String nomeItem;

    @NotBlank(message = "O tipo do item não pode ficar em branco")
    private String tipoItem;

    @NotBlank(message = "A classificação do item não pode ficar em branco")
    private String classificacaoItem;

    @NotNull(message = "O tamanho do item não pode ficar em branco")
    private double tamanhoItem;

    @NotNull(message = "O preço do item não pode ficar em branco")
    private double precoItem;
}