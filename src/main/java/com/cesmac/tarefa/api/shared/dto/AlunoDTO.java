package com.cesmac.tarefa.api.shared.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlunoDTO {

    private Long id;

    @NotBlank private String nome;

    @NotNull private Integer idade;

    @NotBlank
    @Length(min = 6, max = 6)
    private String matricula;

    @NotBlank private String genero;
}
