package com.cesmac.tarefa.api.shared.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class UsuarioDTO implements Serializable {

    @NotNull
    private String login;

    @NotNull
    private String senha;
}
