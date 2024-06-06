package com.cesmac.tarefa.api.repository;

import com.cesmac.tarefa.api.entity.Tarefa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findAllByDataHoraExclusaoIsNullOrderByDataHoraUltimaAlteracaoDesc();

    Optional<Tarefa> findByIdAndDataHoraExclusaoIsNull(Long id);
}
