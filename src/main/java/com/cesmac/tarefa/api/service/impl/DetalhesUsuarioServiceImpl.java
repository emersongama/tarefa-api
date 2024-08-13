package com.cesmac.tarefa.api.service.impl;

import com.cesmac.tarefa.api.configuration.jwt.UsuarioSistema;
import com.cesmac.tarefa.api.entity.Usuario;
import com.cesmac.tarefa.api.repository.UsuarioRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service("detalhesUsuarioService")
public class DetalhesUsuarioServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public DetalhesUsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Usuario usuario = consultarPorCpf(cpf);
        return retornarDetalhes(usuario);
    }

    private Usuario consultarPorCpf(String login) {
        return this.usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new ServiceException("Usuário não encontrado."));
    }

    private UserDetails retornarDetalhes(Usuario usuario) {
        return new UsuarioSistema(usuario, obterPermissoes());
    }

    private Collection<? extends GrantedAuthority> obterPermissoes() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_GERAL"));
        return authorities;
    }
}
