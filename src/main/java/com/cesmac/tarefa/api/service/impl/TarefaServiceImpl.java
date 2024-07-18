package com.cesmac.tarefa.api.service.impl;

import static com.cesmac.tarefa.api.service.impl.validacoes.TarefaValidacoes.validarIdTarefa;
import static com.cesmac.tarefa.api.shared.uteis.ExecutarUtil.*;

import com.cesmac.tarefa.api.configuration.exceptions.ValidacaoNotFoundException;
import com.cesmac.tarefa.api.entity.Tarefa;
import com.cesmac.tarefa.api.repository.TarefaRepository;
import com.cesmac.tarefa.api.service.GrupoService;
import com.cesmac.tarefa.api.service.TarefaService;
import com.cesmac.tarefa.api.shared.EValidacao;
import com.cesmac.tarefa.api.shared.dto.TarefaDTO;
import com.cesmac.tarefa.api.shared.parse.TarefaParse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TarefaServiceImpl implements TarefaService {

    private final TarefaRepository tarefaRepository;

    private final GrupoService grupoService;
    private final ModelMapper mapper;

    public TarefaServiceImpl(TarefaRepository tarefaRepository, GrupoService grupoService) {
        this.tarefaRepository = tarefaRepository;
        this.grupoService = grupoService;
        this.mapper = new ModelMapper();
    }

    @Override
    public TarefaDTO salvar(TarefaDTO tarefaDTO, Long idGrupo) {
        return executarComandoComTratamentoErroComMensagem(
                () -> {
                    tarefaDTO.setId(null);
                    Tarefa tarefa = new TarefaParse().converterParaEntidade(tarefaDTO);
                    tarefa.setGrupo(grupoService.buscarPorId(idGrupo));
                    return new TarefaParse().converterParaDTO(this.tarefaRepository.save(tarefa));
                },
                "Erro ao cadastrar tarefa");
    }

    @Override
    public Tarefa alterar(TarefaDTO tarefaDTO, Long id) {
        return executarComandoComTratamentoErroComMensagem(
                () -> {
                    Tarefa tarefaConsultada = buscarPorId(id);
                    tarefaDTO.setId(tarefaConsultada.getId());
                    return this.tarefaRepository.save(
                            new TarefaParse().converterParaEntidade(tarefaDTO));
                },
                "Erro ao alterar tarefa");
    }

    @Override
    public void concluir(Long id) {
        executarComandoComTratamentoSemRetornoComMensagem(
                () -> {
                    Tarefa tarefaConsultada = buscarPorId(id);
                    tarefaConsultada.setDataHoraConclusao(LocalDateTime.now());
                    this.tarefaRepository.save(tarefaConsultada);
                },
                "Erro ao concluir tarefa");
    }

    @Override
    public void excluir(Long id) {
        executarComandoComTratamentoSemRetornoComMensagem(
                () -> {
                    Tarefa tarefaConsultada = buscarPorId(id);
                    tarefaConsultada.setDataHoraExclusao(LocalDateTime.now());
                    this.tarefaRepository.save(tarefaConsultada);
                },
                "Erro ao excluir tarefa");
    }

    @Override
    public List<TarefaDTO> consultarTodas() {
        return executarComandoComTratamentoErroComMensagem(
                () -> {
                    List<Tarefa> tarefas =
                            this.tarefaRepository
                                    .findAllByDataHoraExclusaoIsNullOrderByDataHoraUltimaAlteracaoDesc();
                    return tarefas.stream()
                            .map(this::converterParaTarefaDTO)
                            .collect(Collectors.toList());
                },
                "Erro ao listar tarefa");
    }

    @Override
    public TarefaDTO buscar(Long id) {
        return executarComandoComTratamentoErroComMensagem(
                () -> {
                    Tarefa tarefaConsultada = buscarPorId(id);
                    return converterParaTarefaDTO(tarefaConsultada);
                },
                "Erro ao buscar tarefa");
    }

    @Override
    public List<TarefaDTO> consultarTarefasPorGrupo(Long idGrupo) {
        return executarComandoComTratamentoErroComMensagem(
                () -> {
                    List<Tarefa> tarefas = this.tarefaRepository.findAllByGrupoId(idGrupo);
                    return tarefas.stream()
                            .map(this::converterParaTarefaDTO)
                            .collect(Collectors.toList());
                },
                "Erro ao listar tarefas por grupo");
    }

    private Tarefa buscarPorId(Long id) {
        validarIdTarefa(id);
        return this.tarefaRepository
                .findByIdAndDataHoraExclusaoIsNull(id)
                .orElseThrow(
                        () ->
                                new ValidacaoNotFoundException(
                                        EValidacao.TARAFA_NAO_LOCALIZADA_POR_ID));
    }

    private TarefaDTO converterParaTarefaDTO(Tarefa tarefa) {
        return this.mapper.map(tarefa, TarefaDTO.class);
    }
}
