package com.cesmac.tarefa.api.shared.parse;

import com.cesmac.tarefa.api.entity.Grupo;
import com.cesmac.tarefa.api.shared.dto.GrupoDTO;

import java.util.List;
import java.util.stream.Collectors;

public class GrupoParse {
    public Grupo converterParaEntidade(GrupoDTO grupoDTO) {
        return Grupo.builder().id(grupoDTO.getId()).nome(grupoDTO.getNome()).build();
    }
    public GrupoDTO converterParaDTOSemAlunosTarefas(Grupo grupo) {
        return GrupoDTO.builder().id(grupo.getId()).nome(grupo.getNome()).build();
    }
    public GrupoDTO converterParaDTO(Grupo grupo) {
        return GrupoDTO.builder()
                .id(grupo.getId())
                .nome(grupo.getNome())
                .alunos(new AlunoParse().converterListaParaDTO(grupo.getAlunos()))
                .tarefas(new TarefaParse().converterListaParaDTO(grupo.getTarefas()))
                .build();
    }

    public List<GrupoDTO> converterListaDTO(List<Grupo> grupos){
        return grupos.stream().map(this::converterParaDTOSemAlunosTarefas).collect(Collectors.toList());
    }


}
