package com.cesmac.tarefa.api.service;

import com.cesmac.tarefa.api.entity.Tarefa;
import com.cesmac.tarefa.api.shared.dto.TarefaDTO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface TarefaService {

    Tarefa salvar(TarefaDTO tarefa);

    Tarefa alterar(TarefaDTO tarefa, Long id);

    void concluir(Long id);

    void excluir(Long id);

    List<TarefaDTO> consultarTodas();

    TarefaDTO buscar(Long id);
}
