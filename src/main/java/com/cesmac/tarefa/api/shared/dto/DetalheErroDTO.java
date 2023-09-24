package com.cesmac.tarefa.api.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
