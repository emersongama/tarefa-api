package com.cesmac.tarefa.api.repository;

import com.cesmac.tarefa.api.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findAllByDataHoraExclusaoIsNullOrderByDataHoraUltimaAlteracaoDesc();

    Optional<Tarefa> findByIdAndDataHoraExclusaoIsNull(Long id);
}
