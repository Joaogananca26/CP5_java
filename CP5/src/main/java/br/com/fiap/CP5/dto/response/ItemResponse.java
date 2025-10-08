package br.com.fiap.CP5.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {

    private UUID idItem;
    private String nomeItem;
    private String tipoItem;
    private String classificacaoItem;
    private double tamanhoItem;
    private double precoItem;

}