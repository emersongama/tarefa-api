package com.cesmac.tarefa.api.resource;

import com.cesmac.tarefa.api.service.AutenticacaoService;
import com.cesmac.tarefa.api.shared.dto.TokenDTO;
import com.cesmac.tarefa.api.shared.dto.UsuarioDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AutenticacaoResource {

    private final Logger LOGGER = LoggerFactory.getLogger(AutenticacaoResource.class);

    private final AutenticacaoService autenticacaoService;

    public AutenticacaoResource(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping("/autenticar")
    public ResponseEntity<TokenDTO> autenticar(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        LOGGER.debug("Requisição REST para autenticar com a api e obter token jwt: {}", usuarioDTO);
        TokenDTO tokenDTO = this.autenticacaoService.autenticar(usuarioDTO);
        return ResponseEntity.ok(tokenDTO);
    }
}
