package com.cesmac.tarefa.api.configuration.jwt;

import com.cesmac.tarefa.api.entity.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class UsuarioSistema extends User {

    private static final long serialVersionUID = 1L;

    private final Usuario usuario;

    public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getLogin(), usuario.getSenha(), authorities);
        this.usuario = usuario;
    }
}
