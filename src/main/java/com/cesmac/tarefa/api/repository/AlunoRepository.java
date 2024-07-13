package com.cesmac.tarefa.api.repository;

import com.cesmac.tarefa.api.entity.Aluno;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    List<Aluno> findAllByDataHoraExclusaoIsNull();

    Optional<Aluno> findByIdAndDataHoraExclusaoIsNull(Long id);
}
