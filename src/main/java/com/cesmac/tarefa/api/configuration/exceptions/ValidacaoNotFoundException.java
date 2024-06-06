package com.cesmac.tarefa.api.configuration.exceptions;

import com.cesmac.tarefa.api.shared.EValidacao;

public class ValidacaoNotFoundException extends ExceptionNotFoundAbstract {

    public ValidacaoNotFoundException(EValidacao validacao, String... params) {
        super(validacao, params);
    }
}
