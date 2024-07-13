package com.cesmac.tarefa.api.shared;

public interface Constantes {

    interface URI {
        String URI_TAREFA = "/api/tarefa";
        String URI_GRUPO = "/api/grupo";
    }

    interface Mensagens {
        String MENSAGEM_TAREFA_NAO_ENCONTRADA = "Tarefa não encontrada.";
        String MSG_ID_OBRIGATORIO = "ID não pode estar nulo!";
        String MENSAGEM_GRUPO_NAO_ENCONTRADO = "Grupo não encontrado.";
        String MENSAGEM_ERRO_CADASTRO_GRUPO = "Erro ao cadastrar grupo";
    }
}
