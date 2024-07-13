package com.cesmac.tarefa.api.shared.dto;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GrupoDTO implements Serializable {

    private Long id;

    @NotEmpty private String nome;

    List<AlunoDTO> alunos;

    List<TarefaDTO> tarefas;
}
