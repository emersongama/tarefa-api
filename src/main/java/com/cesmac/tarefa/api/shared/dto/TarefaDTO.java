package com.cesmac.tarefa.api.shared.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarefaDTO implements Serializable {

    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataHoraConclusao;
}

