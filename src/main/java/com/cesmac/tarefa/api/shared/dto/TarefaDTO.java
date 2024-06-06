package com.cesmac.tarefa.api.shared.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarefaDTO implements Serializable {

    private Long id;

    @NotNull @NotEmpty private String titulo;

    @NotNull @NotEmpty private String descricao;

    private LocalDateTime dataHoraConclusao;

    private LocalDateTime dataHoraUltimaAlteracao;
}
