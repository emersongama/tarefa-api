package com.cesmac.tarefa.api.service.impl.validacoes;

import static com.cesmac.tarefa.api.shared.Constantes.Mensagens.MSG_ID_OBRIGATORIO;

import com.cesmac.tarefa.api.exception.RecursoObrigatorioException;

public class TarefaValidacoes {

    public static void validarIdTarefa(Long id) {
        if (id == null) throw new RecursoObrigatorioException(MSG_ID_OBRIGATORIO);
    }
}
