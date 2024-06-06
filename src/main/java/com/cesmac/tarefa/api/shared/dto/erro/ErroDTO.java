package com.cesmac.tarefa.api.shared.dto.erro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErroDTO {
    private Integer codigo;
    private String mensagem;
}
