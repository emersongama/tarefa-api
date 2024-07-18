package com.cesmac.tarefa.api.shared.parse;

import static java.util.Objects.nonNull;

import com.cesmac.tarefa.api.entity.Tarefa;
import com.cesmac.tarefa.api.shared.dto.TarefaDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public TarefaDTO converterParaDTO(Tarefa tarefa) {
        return TarefaDTO.builder()
                .id(tarefa.getId())
                .titulo(tarefa.getTitulo())
                .descricao(tarefa.getDescricao())
                .dataHoraConclusao(tarefa.getDataHoraConclusao())
                .dataHoraUltimaAlteracao(LocalDateTime.now())
                .grupo(
                        nonNull(tarefa.getGrupo())
                                ? new GrupoParse()
                                        .converterParaDTOSemAlunosTarefas(tarefa.getGrupo())
                                : null)
                .build();
    }

    public TarefaDTO converterParaDTOSemGrupo(Tarefa tarefa) {
        return TarefaDTO.builder()
                .id(tarefa.getId())
                .titulo(tarefa.getTitulo())
                .descricao(tarefa.getDescricao())
                .dataHoraConclusao(tarefa.getDataHoraConclusao())
                .dataHoraUltimaAlteracao(LocalDateTime.now())
                .build();
    }

    public List<TarefaDTO> converterListaParaDTO(List<Tarefa> tarefas) {
        return tarefas.stream().map(this::converterParaDTOSemGrupo).collect(Collectors.toList());
    }
}
