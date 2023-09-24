package com.cesmac.tarefa.api.shared;

public interface Constantes {

    interface URI {
        String URI_TAREFA = "/api/tarefa";
    }

    interface Mensagens {
        String MENSAGEM_TAREFA_NAO_ENCONTRADA = "Tarefa não encontrada.";
        String MSG_ID_OBRIGATORIO = "ID não pode estar nulo!";
    }
}
