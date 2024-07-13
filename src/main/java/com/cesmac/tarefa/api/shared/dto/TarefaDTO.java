package com.cesmac.tarefa.api.shared.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
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

    @NotEmpty private String titulo;

    @NotEmpty private String descricao;

    private LocalDateTime dataHoraConclusao;

    private LocalDateTime dataHoraUltimaAlteracao;

    private GrupoDTO grupo;
}
