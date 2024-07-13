package com.cesmac.tarefa.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
@Table(schema = "public", name = "grupo")
public class Grupo implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    @JoinTable(
            schema = "pubic",
            name = "alunos_grupos",
            joinColumns = {@JoinColumn(name = "id_grupo", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "id_aluno", referencedColumnName = "id")
            })
    private List<Aluno> alunos;

    @Column(name = "data_hora_exclusao")
    private LocalDateTime dataHoraExclusao;
}