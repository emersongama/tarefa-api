package com.cesmac.tarefa.api.shared.parse;

import com.cesmac.tarefa.api.entity.Aluno;
import com.cesmac.tarefa.api.shared.dto.AlunoDTO;
import com.cesmac.tarefa.api.shared.dto.AlunoGrupoDTO;

import java.util.List;
import java.util.stream.Collectors;

public class AlunoParse {
    public Aluno converterParaEntidade(AlunoDTO alunoDTO) {
        return Aluno.builder()
                .id(alunoDTO.getId())
                .nome(alunoDTO.getNome())
                .idade(alunoDTO.getIdade())
                .genero(alunoDTO.getGenero())
                .matricula(alunoDTO.getMatricula())
                .build();
    }
    public AlunoDTO converterParaDTO(Aluno aluno) {
        return AlunoDTO.builder()
                .id(aluno.getId())
                .nome(aluno.getNome())
                .idade(aluno.getIdade())
                .genero(aluno.getGenero())
                .matricula(aluno.getMatricula())
                .build();
    }

    public List<AlunoDTO> converterListaParaDTO(List<Aluno> alunoDTOS){
        return alunoDTOS.stream().map(this::converterParaDTO).collect(Collectors.toList());
    }

}
