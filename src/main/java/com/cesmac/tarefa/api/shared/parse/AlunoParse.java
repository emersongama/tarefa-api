package com.cesmac.tarefa.api.shared.parse;

import com.cesmac.tarefa.api.entity.Aluno;
import com.cesmac.tarefa.api.shared.dto.AlunoDTO;
import com.cesmac.tarefa.api.shared.dto.GrupoDTO;
import com.cesmac.tarefa.api.shared.dto.TarefaDTO;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    public AlunoDTO converterParaDTOComTarefas(Aluno aluno) {
        Set<TarefaDTO> tarefas = new HashSet<>();
        if (!aluno.getGrupos().isEmpty()) {
            List<GrupoDTO> grupos =
                    aluno.getGrupos().stream()
                            .map(grupo -> new GrupoParse().converterParaDTO(grupo))
                            .collect(Collectors.toList());

            for (GrupoDTO grupo : grupos) {
                grupo.getTarefas().forEach(tarefas::add);
            }
        }

        return AlunoDTO.builder()
                .id(aluno.getId())
                .nome(aluno.getNome())
                .idade(aluno.getIdade())
                .genero(aluno.getGenero())
                .matricula(aluno.getMatricula())
                .tarefas(tarefas)
                .build();
    }

    public List<AlunoDTO> converterListaParaDTO(List<Aluno> alunoDTOS) {
        return alunoDTOS.stream().map(this::converterParaDTO).collect(Collectors.toList());
    }
}
