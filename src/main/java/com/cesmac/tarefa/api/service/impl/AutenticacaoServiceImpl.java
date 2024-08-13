package com.cesmac.tarefa.api.service.impl;

import com.cesmac.tarefa.api.configuration.jwt.TokenProvider;
import com.cesmac.tarefa.api.service.AutenticacaoService;
import com.cesmac.tarefa.api.shared.dto.TokenDTO;
import com.cesmac.tarefa.api.shared.dto.UsuarioDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoServiceImpl implements AutenticacaoService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public AutenticacaoServiceImpl(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public TokenDTO autenticar(UsuarioDTO usuarioAutenticacao) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuarioAutenticacao.getLogin(), usuarioAutenticacao.getSenha());
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new TokenDTO(this.tokenProvider.criarToken(authentication));
    }
}
