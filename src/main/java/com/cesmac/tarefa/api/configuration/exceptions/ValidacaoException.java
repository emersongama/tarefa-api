package com.cesmac.tarefa.api.configuration.exceptions;

import com.cesmac.tarefa.api.shared.EValidacao;

public class ValidacaoException extends ExceptionAbstract {

    public ValidacaoException(EValidacao validacao) {
        super(validacao);
    }

    public ValidacaoException(EValidacao validacao, Throwable cause) {
        super(validacao, cause);
    }

    public ValidacaoException(EValidacao validacao, String... params) {
        super(validacao, params);
    }
}
