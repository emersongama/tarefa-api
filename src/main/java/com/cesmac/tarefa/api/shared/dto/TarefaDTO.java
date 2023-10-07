package com.cesmac.tarefa.api.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarefaDTO implements Serializable {

    private Long id;

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    private LocalDateTime dataHoraConclusao;

    private LocalDateTime dataHoraUltimaAlteracao;
}

