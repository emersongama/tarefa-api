package com.cesmac.tarefa.api.shared.parse;

import com.cesmac.tarefa.api.entity.Tarefa;
import com.cesmac.tarefa.api.shared.dto.TarefaDTO;
import java.time.LocalDateTime;

public class TarefaParse {
    public Tarefa converterParaEntidade(TarefaDTO tarefaDTO) {
        return Tarefa.builder()
                .id(tarefaDTO.getId())
                .titulo(tarefaDTO.getTitulo())
                .descricao(tarefaDTO.getDescricao())
                .dataHoraConclusao(tarefaDTO.getDataHoraConclusao())
                .dataHoraUltimaAlteracao(LocalDateTime.now())
                .build();
    }
}
