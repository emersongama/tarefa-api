package com.cesmac.tarefa.api.unitarios.exception;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import com.cesmac.tarefa.api.configuration.exceptions.ExceptionAbstract;
import com.cesmac.tarefa.api.configuration.exceptions.ExceptionNotFoundAbstract;
import com.cesmac.tarefa.api.configuration.exceptions.ValidacaoException;
import com.cesmac.tarefa.api.configuration.exceptions.ValidacaoNotFoundException;
import com.cesmac.tarefa.api.configuration.exceptions.handler.ValidacaoHandler;
import com.cesmac.tarefa.api.shared.EValidacao;
import com.cesmac.tarefa.api.shared.uteis.MensagemUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class ValidacaoHandlerTest {

    @InjectMocks private ValidacaoHandler handler;

    @Test
    @DisplayName("deveria retornar validação não identificada")
    public void deveRetornarValidacaoNaoIdentificada() {
        try (MockedStatic<MensagemUtils> mensagens = Mockito.mockStatic(MensagemUtils.class)) {
            mensagens.when(() -> MensagemUtils.getEnumLabel(any())).thenReturn("teste");

            ExceptionAbstract exception = new ValidacaoException(EValidacao.NAO_IDENTIFICADO);
            ResponseEntity<Object> resultado = handler.validacaoHandle(exception);
            assertEquals(500, resultado.getStatusCode().value());
        }
    }

    @Test
    @DisplayName("deveria retornar validação tratada")
    public void deveRetornarValidacaoTrada() {
        try (MockedStatic<MensagemUtils> mensagens = Mockito.mockStatic(MensagemUtils.class)) {
            mensagens.when(() -> MensagemUtils.getEnumLabel(any())).thenReturn("teste");

            ExceptionAbstract exception =
                    new ValidacaoException(EValidacao.TARAFA_NAO_LOCALIZADA_POR_ID);
            ResponseEntity<Object> resultado = handler.validacaoHandle(exception);
            assertEquals(400, resultado.getStatusCode().value());
        }
    }

    @Test
    @DisplayName(
            "deveria retornar HTTP 404 quando a buscar não retornar resultado da base de dados.")
    public void deveRetornarNotFoundNaoEncontradoNaBaseDados() {
        try (MockedStatic<MensagemUtils> mensagens = Mockito.mockStatic(MensagemUtils.class)) {
            mensagens.when(() -> MensagemUtils.getEnumLabel(any())).thenReturn("teste");
            ExceptionNotFoundAbstract exception =
                    new ValidacaoNotFoundException(EValidacao.NAO_IDENTIFICADO);
            ResponseEntity<Object> resultado = handler.validacaoHandleNotFound(exception);
            assertEquals(404, resultado.getStatusCode().value());
        }
    }
}
