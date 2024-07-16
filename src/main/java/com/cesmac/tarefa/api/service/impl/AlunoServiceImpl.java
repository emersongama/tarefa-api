package com.cesmac.tarefa.api.service.impl;

import com.cesmac.tarefa.api.configuration.exceptions.ValidacaoNotFoundException;
import com.cesmac.tarefa.api.entity.Aluno;
import com.cesmac.tarefa.api.repository.AlunoRepository;
import com.cesmac.tarefa.api.service.AlunoService;
import com.cesmac.tarefa.api.shared.EValidacao;
import com.cesmac.tarefa.api.shared.dto.AlunoDTO;
import com.cesmac.tarefa.api.shared.parse.AlunoParse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.cesmac.tarefa.api.service.impl.validacoes.AlunoValidacoes.validarIdAluno;
import static com.cesmac.tarefa.api.shared.uteis.ExecutarUtil.executarComandoComTratamentoErroComMensagem;
import static com.cesmac.tarefa.api.shared.uteis.ExecutarUtil.executarComandoComTratamentoSemRetornoComMensagem;

@Service
public class AlunoServiceImpl implements AlunoService {

    private final AlunoRepository alunoRepository;
    private final AlunoParse alunoParse;

    public AlunoServiceImpl(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
        this.alunoParse = new AlunoParse();
    }

    @Transactional
    @Override
    public Aluno salvar(AlunoDTO alunoDTO) {
        return executarComandoComTratamentoErroComMensagem(
                () -> {
                    alunoDTO.setId(null);
                    return this.alunoRepository.save(
                            new AlunoParse().converterParaEntidade(alunoDTO));
                },
                "Erro ao cadastrar Aluno");
    }

    @Transactional
    @Override
    public Aluno alterar(AlunoDTO alunoDTO, Long id) {
        return executarComandoComTratamentoErroComMensagem(
                () -> {
                    Aluno alunoConsultado = buscarPorId(id);
                    alunoDTO.setId(alunoConsultado.getId());
                    return this.alunoRepository.save(
                            new AlunoParse().converterParaEntidade(alunoDTO));
                },
                "Erro ao alterar Aluno");
    }

    @Transactional
    @Override
    public void excluir(Long id) {
        executarComandoComTratamentoSemRetornoComMensagem(
                () -> {
                    Aluno aluno = buscarPorId(id);
                    validarExclusao(aluno);
                    aluno.setDataHoraExclusao(LocalDateTime.now());
                    this.alunoRepository.save(aluno);
                },
                "Erro ao excluir aluno");
    }

    @Override
    public List<AlunoDTO> consultarTodas() {
        return executarComandoComTratamentoErroComMensagem(
                () -> {
                    List<Aluno> tarefas = this.alunoRepository.findAllByDataHoraExclusaoIsNull();
                    return tarefas.stream()
                            .map(this.alunoParse::converterParaDTO)
                            .collect(Collectors.toList());
                },
                "Erro ao listar Aluno");
    }

    @Override
    public AlunoDTO buscar(Long id) {
        return executarComandoComTratamentoErroComMensagem(
                () -> {
                    Aluno alunoConsultado = buscarPorId(id);
                    return this.alunoParse.converterParaDTOComTarefas(alunoConsultado);
                },
                "Erro ao buscar Aluno");
    }

    public Aluno buscarPorId(Long id) {
        validarIdAluno(id);
        return this.alunoRepository
                .buscar(id)
                .orElseThrow(() -> new ValidacaoNotFoundException(EValidacao.ALUNO_NAO_LOCALIZADA_POR_ID));
    }
    private void validarExclusao(Aluno aluno) {

    }
}
