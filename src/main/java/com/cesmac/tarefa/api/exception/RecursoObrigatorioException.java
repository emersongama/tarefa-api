package com.cesmac.tarefa.api.exception;

public class RecursoObrigatorioException extends RuntimeException {

    public RecursoObrigatorioException(String mensagem) {
        super(mensagem);
    }

    public RecursoObrigatorioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
