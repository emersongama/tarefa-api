package com.cesmac.tarefa.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
@Table(schema = "public", name = "aluno")
public class Aluno implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String nome;

    @NotNull
    @Column
    private Integer idade;

    @NotNull
    @Column
    private String matricula;

    @NotNull
    @Column
    private String genero;

    @Column(name = "data_hora_exclusao")
    private LocalDateTime dataHoraExclusao;

    @ManyToMany(mappedBy = "alunos")
    private List<Grupo> grupos;
}
