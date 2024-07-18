package com.cesmac.tarefa.api.service.impl;

import static com.cesmac.tarefa.api.service.impl.validacoes.GrupoValidacoes.validarIdGrupo;
import static com.cesmac.tarefa.api.shared.Constantes.Mensagens.MENSAGEM_ERRO_CADASTRO_GRUPO;
import static com.cesmac.tarefa.api.shared.uteis.ExecutarUtil.executarComandoComTratamentoErroComMensagem;
import static com.cesmac.tarefa.api.shared.uteis.ExecutarUtil.executarComandoComTratamentoSemRetornoComMensagem;

import com.cesmac.tarefa.api.configuration.exceptions.ValidacaoNotFoundException;
import com.cesmac.tarefa.api.entity.Aluno;
import com.cesmac.tarefa.api.entity.Grupo;
import com.cesmac.tarefa.api.repository.GrupoRepository;
import com.cesmac.tarefa.api.service.AlunoService;
import com.cesmac.tarefa.api.service.GrupoService;
import com.cesmac.tarefa.api.service.TarefaService;
import com.cesmac.tarefa.api.shared.EValidacao;
import com.cesmac.tarefa.api.shared.dto.AlunoDTO;
import com.cesmac.tarefa.api.shared.dto.AlunoGrupoDTO;
import com.cesmac.tarefa.api.shared.dto.GrupoDTO;
import com.cesmac.tarefa.api.shared.dto.TarefaDTO;
import com.cesmac.tarefa.api.shared.parse.GrupoParse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class GrupoServiceImpl implements GrupoService {

    private final GrupoRepository grupoRepository;
    private final AlunoService alunoService;
    private final TarefaService tarefaService;
    private final ModelMapper mapper;

    public GrupoServiceImpl(GrupoRepository grupoRepository, AlunoService alunoService, TarefaService tarefaService) {
        this.grupoRepository = grupoRepository;
        this.alunoService = alunoService;
        this.tarefaService = tarefaService;
        this.mapper = new ModelMapper();
    }

    @Override
    public Grupo salvar(GrupoDTO grupoDTO) {
        return executarComandoComTratamentoErroComMensagem(
                () -> {
                    grupoDTO.setId(null);
                    return this.grupoRepository.save(
                            new GrupoParse().converterParaEntidade(grupoDTO));
                },
                MENSAGEM_ERRO_CADASTRO_GRUPO);
    }

    @Override
    public Grupo alterar(GrupoDTO grupoDTO, Long id) {
        return executarComandoComTratamentoErroComMensagem(
                () -> {
                    Grupo grupoConsultada = buscarPorId(id);
                    grupoDTO.setId(grupoConsultada.getId());
                    return this.grupoRepository.save(
                            new GrupoParse().converterParaEntidade(grupoDTO));
                },
                "Erro ao alterar grupo");
    }

    @Override
    public void excluir(Long id) {
        executarComandoComTratamentoSemRetornoComMensagem(
                () -> {
                    Grupo grupoConsultada = buscarPorId(id);

                    validarExclusaoGrupo(consultarAlunosDoGrupo(id), this.tarefaService.consultarTarefasPorGrupo(id));

                    grupoConsultada.setDataHoraExclusao(LocalDateTime.now());
                    this.grupoRepository.save(grupoConsultada);
                },
                "Erro ao excluir grupo");
    }

    @Override
    public List<GrupoDTO> consultarTodas() {
        return executarComandoComTratamentoErroComMensagem(
                () -> {
                    List<Grupo> grupos =
                            this.grupoRepository.findAllByDataHoraExclusaoIsNullOrderByNome();
                    return new GrupoParse().converterListaDTO(grupos);
                },
                "Erro ao listar grupo");
    }

    @Override
    public GrupoDTO buscar(Long id) {
        return executarComandoComTratamentoErroComMensagem(
                () -> {
                    Grupo grupoConsultada = buscarPorId(id);
                    GrupoDTO retorno = new GrupoParse().converterParaDTO(grupoConsultada);
                    retorno.setAlunos(consultarAlunosDoGrupo(id));
                    return retorno;
                },
                "Erro ao buscar grupo");
    }

    @Override
    public Grupo buscarPorId(Long id) {
        validarIdGrupo(id);

        return this.grupoRepository
                .findAllByIdAndDataHoraExclusaoIsNull(id)
                .orElseThrow(
                        () ->
                                new ValidacaoNotFoundException(
                                        EValidacao.GRUPO_NAO_LOCALIZADA_POR_ID));
    }

    @Override
    public List<AlunoDTO> consultarAlunosDoGrupo(Long idGrupo) {
        return executarComandoComTratamentoErroComMensagem(
                () -> {
                    List<Aluno> listaAlunos = this.grupoRepository.listarAlunosDoGrupo(idGrupo);
                    return listaAlunos.stream()
                            .map(this::converterParaAlunoDTO)
                            .collect(Collectors.toList());
                },
                "Erro ao listar alunos do grupo");
    }

    @Override
    public void vincular(AlunoGrupoDTO dto) {
        executarComandoComTratamentoSemRetornoComMensagem(
                () -> {
                    Grupo grupo = buscarPorId(dto.getIdGrupo());
                    Aluno aluno = alunoService.buscarPorId(dto.getIdAluno());
                    grupo.getAlunos().add(aluno);
                    this.grupoRepository.save(grupo);
                },
                "Erro ao vincular aluno no grupo");
    }

    @Override
    public void desvincular(AlunoGrupoDTO alunoGrupoDTO) {
        executarComandoComTratamentoSemRetornoComMensagem(() -> {
            Grupo grupo = buscarPorId(alunoGrupoDTO.getIdGrupo());
            Aluno aluno = this.alunoService.buscarPorId(alunoGrupoDTO.getIdAluno());
            validarExclusaoVinculo(grupo, aluno);
            grupo.getAlunos().remove(aluno);
            this.grupoRepository.save(grupo);
        }, "Erro ao desvincular aluno no grupo");
    }

    private AlunoDTO converterParaAlunoDTO(Aluno aluno) {
        return this.mapper.map(aluno, AlunoDTO.class);
    }

    private void validarExclusaoVinculo(Grupo grupo, Aluno aluno) {
        if (!grupo.getAlunos().contains(aluno)) throw new ValidacaoNotFoundException(EValidacao.ALUNO_NAO_LOCALIZADO_GRUPO);
    }

    private void validarExclusaoGrupo(List<AlunoDTO> listaAlunos, List<TarefaDTO> listaTarefas) {
        if (!listaAlunos.isEmpty()) throw new ValidacaoNotFoundException(EValidacao.GRUPO_POSSUI_ALUNOS_ASSOCIADOS);
        if (!listaTarefas.isEmpty()) throw new ValidacaoNotFoundException(EValidacao.GRUPO_POSSUI_TAREFAS_ASSOCIADOS);
    }
}
