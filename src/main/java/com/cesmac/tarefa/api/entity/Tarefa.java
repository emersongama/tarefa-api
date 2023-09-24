package com.cesmac.tarefa.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "public", name = "tarefa")
@Entity
@Builder
public class Tarefa implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @NotEmpty
    @Column
    private String titulo;

    @NotNull @NotEmpty
    @Column
    private String descricao;

    @Column(name = "data_hora_conclusao")
    private LocalDateTime dataHoraConclusao;

    @Column(name = "data_hora_exclusao")
    private LocalDateTime dataHoraExclusao;
}
