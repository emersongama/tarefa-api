package com.cesmac.tarefa.api.shared.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AlunoGrupoDTO implements Serializable {

    @NotNull private Long idAluno;
    @NotNull private Long idGrupo;
}
