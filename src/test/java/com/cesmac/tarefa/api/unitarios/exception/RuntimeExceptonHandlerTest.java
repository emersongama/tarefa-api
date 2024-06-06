package com.cesmac.tarefa.api.unitarios.exception;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import com.cesmac.tarefa.api.configuration.exceptions.ApiTarefaRuntimeException;
import com.cesmac.tarefa.api.configuration.exceptions.handler.RuntimeExceptionHandler;
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
public class RuntimeExceptonHandlerTest {

    @InjectMocks private RuntimeExceptionHandler handler;

    @Test
    @DisplayName("deveria retornar validação não identificada")
    public void deveRetornarValidacaoNaoIdentificada() {
        try (MockedStatic<MensagemUtils> mensagens = Mockito.mockStatic(MensagemUtils.class)) {
            mensagens.when(() -> MensagemUtils.getEnumLabel(any())).thenReturn("teste");

            ApiTarefaRuntimeException exception =
                    new ApiTarefaRuntimeException(EValidacao.NAO_IDENTIFICADO);
            ResponseEntity<Object> resultado = handler.validacaoHandle(exception);
            assertEquals(500, resultado.getStatusCode().value());
        }
    }
}
