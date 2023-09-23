package com.cesmac.tarefa.api.service;

import com.cesmac.tarefa.api.entity.Tarefa;
import com.cesmac.tarefa.api.repository.TarefaRepository;
import com.cesmac.tarefa.api.shared.dto.TarefaDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarefaServiceImpl implements TarefaService {

    private TarefaRepository tarefaRepository;
    private ModelMapper mapper;

    public TarefaServiceImpl(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public Tarefa salvar(TarefaDTO tarefa) {
        return this.tarefaRepository.save(Tarefa.builder()
                .titulo(tarefa.getTitulo())
                .descricao(tarefa.getDescricao())
                .build());
    }

    @Override
    public Tarefa alterar(TarefaDTO tarefa, Long id) {
        Tarefa tarefaConsultada = buscarPorId(id);
        tarefaConsultada.setTitulo(tarefa.getTitulo());
        tarefaConsultada.setDescricao(tarefa.getDescricao());
        return tarefaConsultada;
    }

    @Override
    public void concluir(Long id) {
        Tarefa tarefaConsultada = buscarPorId(id);
        tarefaConsultada.setDataHoraConclusao(LocalDateTime.now());
    }

    @Override
    public void excluir(Long id) {
        Tarefa tarefaConsultada = buscarPorId(id);
        tarefaConsultada.setDataHoraExclusao(LocalDateTime.now());
    }

    @Override
    public List<TarefaDTO> consultarTodas() {
        List<Tarefa> tarefas = this.tarefaRepository.findAll();
        return tarefas.stream().map(t -> mapper.map(t, TarefaDTO.class)).collect(Collectors.toList());
    }

    @Override
    public Tarefa buscarPorId(Long id) {
        return this.tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada."));
    }
}
