package com.cesmac.tarefa.api.service;

import com.cesmac.tarefa.api.entity.Tarefa;
import com.cesmac.tarefa.api.shared.dto.TarefaDTO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface TarefaService {

    TarefaDTO salvar(TarefaDTO tarefa, Long idGrupo);

    Tarefa alterar(TarefaDTO tarefa, Long id);

    void concluir(Long id);

    void excluir(Long id);

    List<TarefaDTO> consultarTodas();

    TarefaDTO buscar(Long id);

    List<TarefaDTO> consultarTarefasPorGrupo(Long idGrupo);
}
