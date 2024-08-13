package com.cesmac.tarefa.api.service;

import com.cesmac.tarefa.api.shared.dto.TokenDTO;
import com.cesmac.tarefa.api.shared.dto.UsuarioDTO;

public interface AutenticacaoService {

    TokenDTO autenticar(UsuarioDTO usuarioAutenticacao);
}
