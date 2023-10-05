package com.cesmac.tarefa.api.repository;

import com.cesmac.tarefa.api.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.cesmac.tarefa.api.shared.Hqls.SQL_CONSULTAR_TAREFAS_ATIVAS;
import static com.cesmac.tarefa.api.shared.Hqls.SQL_CONSULTAR_TAREFAS_ATIVAS_POR_ID;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    @Override
    @Query(SQL_CONSULTAR_TAREFAS_ATIVAS)
    List<Tarefa> findAll();

    @Override
    @Query(SQL_CONSULTAR_TAREFAS_ATIVAS_POR_ID)
    Optional<Tarefa> findById(Long id);
}
