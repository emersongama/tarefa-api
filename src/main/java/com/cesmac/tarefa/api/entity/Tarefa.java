package com.cesmac.tarefa.api.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column private String titulo;

    @Column private String descricao;

    @Column(name = "data_hora_conclusao")
    private LocalDateTime dataHoraConclusao;

    @Column(name = "data_hora_exclusao")
    private LocalDateTime dataHoraExclusao;

    @Column(name = "data_hora_ultima_alteracao")
    private LocalDateTime dataHoraUltimaAlteracao;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;
}
