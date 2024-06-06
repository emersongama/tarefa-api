package com.cesmac.tarefa.api.configuration.exceptions;

import com.cesmac.tarefa.api.shared.EValidacao;
import lombok.Getter;

@Getter
public class ApiTarefaRuntimeException extends RuntimeException {
    @Getter private String descricao;

    public ApiTarefaRuntimeException(String msg) {
        super(msg);
        this.descricao = msg;
    }

    public ApiTarefaRuntimeException(EValidacao validacao) {
        super(validacao.getDescricao());
        this.descricao = validacao.getDescricao();
    }

    public ApiTarefaRuntimeException(String msg, Throwable causa) {
        super(msg, causa);
        this.descricao = msg;
    }
}
