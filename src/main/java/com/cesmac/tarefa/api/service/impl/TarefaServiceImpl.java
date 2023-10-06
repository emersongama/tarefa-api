package com.cesmac.tarefa.api.service.impl;

import com.cesmac.tarefa.api.entity.Tarefa;
import com.cesmac.tarefa.api.exception.RecursoNaoEncontradoException;
import com.cesmac.tarefa.api.repository.TarefaRepository;
import com.cesmac.tarefa.api.service.TarefaService;
import com.cesmac.tarefa.api.service.impl.validacoes.TarefaValidacoes;
import com.cesmac.tarefa.api.shared.dto.TarefaDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.cesmac.tarefa.api.shared.Constantes.Mensagens.MENSAGEM_TAREFA_NAO_ENCONTRADA;

@Service
public class TarefaServiceImpl implements TarefaService {

    private final TarefaRepository tarefaRepository;
    private final ModelMapper mapper;

    public TarefaServiceImpl(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public Tarefa salvar(TarefaDTO tarefaDTO) {
        tarefaDTO.setId(null);
        return this.tarefaRepository.save(converterParaTarefa(tarefaDTO));
    }

    @Override
    public Tarefa alterar(TarefaDTO tarefaDTO, Long id) {
        Tarefa tarefaConsultada = buscarPorId(id);
        tarefaDTO.setId(tarefaConsultada.getId());
        return this.tarefaRepository.save(converterParaTarefa(tarefaDTO));
    }

    @Override
    public void concluir(Long id) {
        Tarefa tarefaConsultada = buscarPorId(id);
        tarefaConsultada.setDataHoraConclusao(LocalDateTime.now());
        this.tarefaRepository.save(tarefaConsultada);
    }

    @Override
    public void excluir(Long id) {
        Tarefa tarefaConsultada = buscarPorId(id);
        tarefaConsultada.setDataHoraExclusao(LocalDateTime.now());
        this.tarefaRepository.save(tarefaConsultada);
    }

    @Override
    public List<TarefaDTO> consultarTodas() {
        List<Tarefa> tarefas = this.tarefaRepository.findAllByDataHoraExclusaoIsNullOrderByDataHoraUltimaAlteracaoDesc();
        return tarefas.stream().map(this::converterParaTarefaDTO).collect(Collectors.toList());
    }

    @Override
    public TarefaDTO buscar(Long id) {
        Tarefa tarefaConsultada = buscarPorId(id);
        return converterParaTarefaDTO(tarefaConsultada);
    }

    private Tarefa buscarPorId(Long id) {
        TarefaValidacoes.validarIdTarefa(id);

        return this.tarefaRepository.findByIdAndDataHoraExclusaoIsNull(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(MENSAGEM_TAREFA_NAO_ENCONTRADA));
    }

    private TarefaDTO converterParaTarefaDTO(Tarefa tarefa) {
        return this.mapper.map(tarefa, TarefaDTO.class);
    }

    private Tarefa converterParaTarefa(TarefaDTO tarefaDTO) {
        return Tarefa.builder()
                .id(tarefaDTO.getId())
                .titulo(tarefaDTO.getTitulo())
                .descricao(tarefaDTO.getDescricao())
                .dataHoraConclusao(tarefaDTO.getDataHoraConclusao())
                .dataHoraUltimaAlteracao(LocalDateTime.now())
                .build();
    }
}
