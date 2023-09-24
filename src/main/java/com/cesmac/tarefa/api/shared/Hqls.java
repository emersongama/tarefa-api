package com.cesmac.tarefa.api.shared;

public interface Hqls {

    String SQL_CONSULTAR_TAREFAS_ATIVAS =
            "select t from Tarefa t where t.dataHoraExclusao IS NULL";

    String SQL_CONSULTAR_TAREFAS_ATIVAS_POR_ID =
            "select t from Tarefa t where t.dataHoraExclusao IS NULL AND t.id = :id";
}
