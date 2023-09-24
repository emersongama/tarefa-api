package com.cesmac.tarefa.api.handler;

import com.cesmac.tarefa.api.exception.RecursoNaoEncontradoException;
import com.cesmac.tarefa.api.exception.RecursoObrigatorioException;
import com.cesmac.tarefa.api.shared.dto.DetalheErroDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class TarefaExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<DetalheErroDTO> handleRecursoNaoEncontradoException(RecursoNaoEncontradoException ex) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(httpStatus).body(obterDetalhesErroDTO(ex, httpStatus));
    }

    @ExceptionHandler(RecursoObrigatorioException.class)
    public ResponseEntity<DetalheErroDTO> handleCnpjException(RecursoObrigatorioException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).body(obterDetalhesErroDTO(ex, httpStatus));
    }

    @ExceptionHandler({SQLException.class, NullPointerException.class, RuntimeException.class})
    public ResponseEntity<DetalheErroDTO> handleNullPointerException(Exception ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(httpStatus).body(obterDetalhesErroDTO(ex, httpStatus));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DetalheErroDTO>> handleValidationErros(MethodArgumentNotValidException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        List<DetalheErroDTO> listaErros = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(campo -> obterDetalhesErroDTO(campo, httpStatus))
                .collect(Collectors.toList());
        return ResponseEntity.status(httpStatus).body(listaErros);
    }

    private DetalheErroDTO obterDetalhesErroDTO(Exception ex, HttpStatus httpStatus) {
        return new DetalheErroDTO()
                .builder()
                .campo(ex.getClass().getSimpleName())
                .mensagem(ex.getMessage())
                .status(httpStatus.value())
                .tempo(System.currentTimeMillis())
                .build();
    }

    private DetalheErroDTO obterDetalhesErroDTO(FieldError campo, HttpStatus httpStatus) {
        return new DetalheErroDTO()
                .builder()
                .campo(campo.getField())
                .mensagem(campo.getDefaultMessage())
                .status(httpStatus.value())
                .tempo(System.currentTimeMillis())
                .build();
    }
}
