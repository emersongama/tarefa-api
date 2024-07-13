package com.cesmac.tarefa.api.shared.parse;

import com.cesmac.tarefa.api.entity.Aluno;
import com.cesmac.tarefa.api.shared.dto.AlunoDTO;

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
}