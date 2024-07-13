package com.cesmac.tarefa.api.service.impl;

import com.cesmac.tarefa.api.entity.Aluno;
import com.cesmac.tarefa.api.exception.RecursoNaoEncontradoException;
import com.cesmac.tarefa.api.repository.AlunoRepository;
import com.cesmac.tarefa.api.service.AlunoService;
import com.cesmac.tarefa.api.shared.dto.AlunoDTO;
import com.cesmac.tarefa.api.shared.parse.AlunoParse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.cesmac.tarefa.api.service.impl.validacoes.AlunoValidacoes.validarIdAluno;
import static com.cesmac.tarefa.api.shared.Constantes.Mensagens.MENSAGEM_ALUNO_NAO_ENCONTRADO;
import static com.cesmac.tarefa.api.shared.uteis.ExecutarUtil.executarComandoComTratamentoErroComMensagem;
import static com.cesmac.tarefa.api.shared.uteis.ExecutarUtil.executarComandoComTratamentoSemRetornoComMensagem;

@Service
public class AlunoServiceImpl implements AlunoService {

    private final AlunoRepository alunoRepository;
    private final ModelMapper mapper;

    public AlunoServiceImpl(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
        this.mapper = new ModelMapper();
    }

    @Transactional
    @Override
    public Aluno salvar(AlunoDTO alunoDTO) {
        return executarComandoComTratamentoErroComMensagem(() -> {
            alunoDTO.setId(null);
            return this.alunoRepository.save(
                    new AlunoParse().converterParaEntidade(alunoDTO));
        }, "Erro ao cadastrar Aluno");
    }

    @Transactional
    @Override
    public Aluno alterar(AlunoDTO alunoDTO, Long id) {
        return executarComandoComTratamentoErroComMensagem(() -> {
            Aluno alunoConsultado = buscarPorId(id);
            alunoDTO.setId(alunoConsultado.getId());
            return this.alunoRepository.save(new AlunoParse().converterParaEntidade(alunoDTO));
        }, "Erro ao alterar Aluno");
    }

    @Transactional
    @Override
    public void excluir(Long id) {
        executarComandoComTratamentoSemRetornoComMensagem(() -> {
            Aluno alunoConsultado = buscarPorId(id);
            alunoConsultado.setDataHoraExclusao(LocalDateTime.now());
            this.alunoRepository.save(alunoConsultado);
        }, "Erro ao excluir aluno");
    }

    @Override
    public List<AlunoDTO> consultarTodas() {
        return executarComandoComTratamentoErroComMensagem(() -> {
            List<Aluno> tarefas = this.alunoRepository.findAllByDataHoraExclusaoIsNull();
            return tarefas.stream()
                    .map(this::converterParaAlunoDTO)
                    .collect(Collectors.toList());
        }, "Erro ao listar Aluno");
    }

    @Override
    public AlunoDTO buscar(Long id) {
        return executarComandoComTratamentoErroComMensagem(() -> {
            Aluno alunoConsultado = buscarPorId(id);
            return converterParaAlunoDTO(alunoConsultado);
        }, "Erro ao buscar Aluno");
    }

    private Aluno buscarPorId(Long id) {
        validarIdAluno(id);
        return this.alunoRepository
                .findByIdAndDataHoraExclusaoIsNull(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(MENSAGEM_ALUNO_NAO_ENCONTRADO));
    }

    private AlunoDTO converterParaAlunoDTO(Aluno aluno) {
        return this.mapper.map(aluno, AlunoDTO.class);
    }
}