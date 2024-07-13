package com.cesmac.tarefa.api.service;

import com.cesmac.tarefa.api.entity.Aluno;
import com.cesmac.tarefa.api.entity.Grupo;
import com.cesmac.tarefa.api.shared.dto.AlunoDTO;
import com.cesmac.tarefa.api.shared.dto.GrupoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GrupoService {

    Grupo salvar(GrupoDTO grupoDTO);

    Grupo alterar(GrupoDTO grupoDTO, Long id);

    void excluir(Long id);

    List<GrupoDTO> consultarTodas();

    GrupoDTO buscar(Long id);

    List<AlunoDTO> consultarAlunosDoGrupo(Long idGrupo);
}
