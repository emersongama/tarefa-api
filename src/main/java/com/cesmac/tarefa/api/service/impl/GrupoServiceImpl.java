package com.cesmac.tarefa.api.service.impl;

import com.cesmac.tarefa.api.entity.Aluno;
import com.cesmac.tarefa.api.entity.Grupo;
import com.cesmac.tarefa.api.exception.RecursoNaoEncontradoException;
import com.cesmac.tarefa.api.repository.GrupoRepository;
import com.cesmac.tarefa.api.service.GrupoService;
import com.cesmac.tarefa.api.shared.dto.GrupoDTO;
import com.cesmac.tarefa.api.shared.parse.GrupoParse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.cesmac.tarefa.api.service.impl.validacoes.GrupoValidacoes.validarIdGrupo;
import static com.cesmac.tarefa.api.shared.Constantes.Mensagens.MENSAGEM_GRUPO_NAO_ENCONTRADO;
import static com.cesmac.tarefa.api.shared.Constantes.Mensagens.MENSAGEM_ERRO_CADASTRO_GRUPO;
import static com.cesmac.tarefa.api.shared.uteis.ExecutarUtil.executarComandoComTratamentoErroComMensagem;
import static com.cesmac.tarefa.api.shared.uteis.ExecutarUtil.executarComandoComTratamentoSemRetornoComMensagem;

@Service
public class GrupoServiceImpl implements GrupoService {

    private final GrupoRepository grupoRepository;
    private final ModelMapper mapper;

    public GrupoServiceImpl(GrupoRepository grupoRepository) {
        this.grupoRepository = grupoRepository;
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
                    this.grupoRepository.save(grupoConsultada);
                },
                "Erro ao excluir grupo");
    }

    @Override
    public List<GrupoDTO> consultarTodas() {
        return executarComandoComTratamentoErroComMensagem(
                () -> {
                    List<Grupo> grupos =
                            this.grupoRepository.findAllOrderByNome();
                    return grupos.stream()
                            .map(this::converterParaGrupoDTO)
                            .collect(Collectors.toList());
                },
                "Erro ao listar grupo");
    }

    @Override
    public GrupoDTO buscar(Long id) {
        return executarComandoComTratamentoErroComMensagem(
                () -> {
                    Grupo grupoConsultada = buscarPorId(id);
                    return converterParaGrupoDTO(grupoConsultada);
                },
                "Erro ao buscar grupo");
    }

    private Grupo buscarPorId(Long id) {
        validarIdGrupo(id);

        return this.grupoRepository
                .findById(id)
                .orElseThrow(
                        () -> new RecursoNaoEncontradoException(MENSAGEM_GRUPO_NAO_ENCONTRADO));
    }

    @Override
    public List<Aluno> consultarAlunosDoGrupo(Long idGrupo) {
        return executarComandoComTratamentoErroComMensagem(
                () -> {
                    return this.grupoRepository.listarAlunosDoGrupo(idGrupo);
//                    return listaAlunos.stream()
//                            .map(this::converterParaGrupoDTO)
//                            .collect(Collectors.toList());
                },
                "Erro ao listar alunos do grupo");
    }

    private GrupoDTO converterParaGrupoDTO(Grupo grupo) {
        return this.mapper.map(grupo, GrupoDTO.class);
    }
}
