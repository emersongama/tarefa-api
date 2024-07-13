package com.cesmac.tarefa.api.service.impl.validacoes;

import com.cesmac.tarefa.api.exception.RecursoObrigatorioException;

import static com.cesmac.tarefa.api.shared.Constantes.Mensagens.MSG_ID_OBRIGATORIO;

public class AlunoValidacoes {

    public static void validarIdAluno(Long id) {
        if (id == null) throw new RecursoObrigatorioException(MSG_ID_OBRIGATORIO);
    }
}
