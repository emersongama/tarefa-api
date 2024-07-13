package com.cesmac.tarefa.api.service;

import com.cesmac.tarefa.api.entity.Aluno;
import com.cesmac.tarefa.api.shared.dto.AlunoDTO;
import java.util.List;

public interface AlunoService {

    Aluno salvar(AlunoDTO dto);

    Aluno alterar(AlunoDTO dto, Long id);

    void excluir(Long id);

    List<AlunoDTO> consultarTodas();

    AlunoDTO buscar(Long id);

    Aluno buscarPorId(Long id);
}
