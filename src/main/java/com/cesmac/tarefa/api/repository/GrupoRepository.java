package com.cesmac.tarefa.api.repository;

import com.cesmac.tarefa.api.entity.Aluno;
import com.cesmac.tarefa.api.entity.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {


    List<Grupo> findAllByDataHoraExclusaoIsNullOrderByNome();

    Optional<Grupo> findAllByIdAndDataHoraExclusaoIsNull(Long id);

    @Query(value = "SELECT a FROM Aluno a JOIN a.grupos g WHERE g.id = :idGrupo")
    List<Aluno> listarAlunosDoGrupo(Long idGrupo);
}
