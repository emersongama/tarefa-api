package com.cesmac.tarefa.api.shared.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalheErroDTO implements Serializable {

    private Integer status;
    private String campo;
    private String mensagem;
    private Long tempo;
}
