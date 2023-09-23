package com.cesmac.tarefa.api.service;

import com.cesmac.tarefa.api.entity.Tarefa;
import com.cesmac.tarefa.api.shared.dto.TarefaDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TarefaService {

    Tarefa salvar(TarefaDTO tarefa);

    Tarefa alterar(TarefaDTO tarefa, Long id);

    void concluir(Long id);

    void excluir(Long id);

    List<TarefaDTO> consultarTodas();

    Tarefa buscarPorId(Long id);
}
