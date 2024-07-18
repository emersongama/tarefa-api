package com.cesmac.tarefa.api.repository;

import com.cesmac.tarefa.api.entity.Aluno;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    List<Aluno> findAllByDataHoraExclusaoIsNull();

    Optional<Aluno> findByIdAndDataHoraExclusaoIsNull(Long id);

    @Query(
            "select a from Aluno a left join fetch a.grupos g left join g.tarefas where a.id = :id and a.dataHoraExclusao is null")
    Optional<Aluno> buscar(Long id);
}
