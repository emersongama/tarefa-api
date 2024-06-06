package com.cesmac.tarefa.api.configuration.exceptions;

public class DeserializacaoException extends RuntimeException {
    public DeserializacaoException(String mensagem, Throwable erroOriginal) {
        super(mensagem, erroOriginal);
    }
}
