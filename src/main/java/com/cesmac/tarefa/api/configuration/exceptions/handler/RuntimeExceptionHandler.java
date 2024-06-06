package com.cesmac.tarefa.api.configuration.exceptions.handler;

import com.cesmac.tarefa.api.configuration.exceptions.ApiTarefaRuntimeException;
import com.cesmac.tarefa.api.shared.dto.erro.ErroDTO;
import com.cesmac.tarefa.api.shared.dto.erro.ErrosDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RuntimeExceptionHandler {

    @ExceptionHandler(ApiTarefaRuntimeException.class)
    public ResponseEntity<Object> validacaoHandle(ApiTarefaRuntimeException ex) {
        List<ErroDTO> erros = new ArrayList<>();

        erros.add(ErroDTO.builder().codigo(-999).mensagem(ex.getDescricao()).build());
        log.error("Ocorreu um erro interno", ex);

        return new ResponseEntity<>(
                ErrosDTO.builder().erros(erros).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
