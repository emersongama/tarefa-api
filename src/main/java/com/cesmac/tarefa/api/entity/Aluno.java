package com.cesmac.tarefa.api.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity
@Table(schema = "public", name = "aluno")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Aluno implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Column private String nome;

    @NotNull @Column private Integer idade;

    @NotNull @Column private String matricula;

    @NotNull @Column private String genero;

    @Column(name = "data_hora_exclusao")
    private LocalDateTime dataHoraExclusao;

    @ManyToMany(mappedBy = "alunos", fetch = FetchType.LAZY)
    private List<Grupo> grupos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aluno aluno = (Aluno) o;
        return Objects.equals(id, aluno.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
