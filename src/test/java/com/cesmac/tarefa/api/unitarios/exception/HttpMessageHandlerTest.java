package com.cesmac.tarefa.api.unitarios.exception;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.cesmac.tarefa.api.configuration.exceptions.ValidacaoException;
import com.cesmac.tarefa.api.configuration.exceptions.handler.HttpMessageHandler;
import com.cesmac.tarefa.api.shared.EValidacao;
import com.cesmac.tarefa.api.shared.dto.erro.ErrosDTO;
import com.cesmac.tarefa.api.shared.uteis.MensagemUtils;
import com.cesmac.tarefa.api.shared.uteis.ObjectMapperUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.http.MockHttpInputMessage;

@ExtendWith(MockitoExtension.class)
public class HttpMessageHandlerTest {

    @InjectMocks private HttpMessageHandler handler;

    @Mock HttpMessageNotReadableException httpExceptionMock;
    @Mock MismatchedInputException mismatchedExceptionMock;
    @Mock JsonMappingException.Reference referenceExceptionMock;

    @Test
    @DisplayName("deveria retornar o erro de validação de campos padrão")
    public void deveriaRetornarErroPadrao() throws JsonProcessingException {
        try (MockedStatic<MensagemUtils> mensagens = Mockito.mockStatic(MensagemUtils.class)) {
            mensagens.when(() -> MensagemUtils.getEnumLabel(any())).thenReturn("teste");

            InputStream is = new ByteArrayInputStream("Teste".getBytes(StandardCharsets.UTF_8));
            HttpInputMessage inputMessage = new MockHttpInputMessage(is);
            HttpMessageNotReadableException ex =
                    new HttpMessageNotReadableException("", inputMessage);

            ResponseEntity<Object> retorno = handler.handleHttpMessageException(ex);

            assertNotNull(retorno);
            assertEquals(400, retorno.getStatusCode().value());

            String body = ObjectMapperUtil.asJsonString(retorno.getBody());
            ErrosDTO errosDTO =
                    new ObjectMapperUtil().objectMapper().readValue(body, ErrosDTO.class);

            assertEquals(-1, errosDTO.getErros().get(0).getCodigo());
        }
    }

    @Test
    @DisplayName("deveria retornar o erro de validação de campo específico")
    public void deveriaRetornarErroDeCampoEspecifico() throws JsonProcessingException {
        try (MockedStatic<MensagemUtils> mensagens = Mockito.mockStatic(MensagemUtils.class)) {
            mensagens.when(() -> MensagemUtils.getMensagem(any())).thenReturn("teste");

            List<JsonMappingException.Reference> refLst = new ArrayList<>();
            refLst.add(referenceExceptionMock);

            doReturn("sigla").when(referenceExceptionMock).getFieldName();
            doReturn(refLst).when(mismatchedExceptionMock).getPath();
            doReturn(mismatchedExceptionMock).when(httpExceptionMock).getCause();

            ResponseEntity<Object> retorno = handler.handleHttpMessageException(httpExceptionMock);

            assertNotNull(retorno);
            assertEquals(400, retorno.getStatusCode().value());

            String body = ObjectMapperUtil.asJsonString(retorno.getBody());
            ErrosDTO errosDTO =
                    new ObjectMapperUtil().objectMapper().readValue(body, ErrosDTO.class);

            assertEquals(-1, errosDTO.getErros().get(0).getCodigo());
        }
    }

    @Test
    @DisplayName("deveria retornar o erro de validação de campos não identificados")
    public void deveriaRetornarErroDeCamposNaoIdentificados() {
        try (MockedStatic<MensagemUtils> mensagens = Mockito.mockStatic(MensagemUtils.class)) {
            mensagens.when(() -> MensagemUtils.getMensagem(any())).thenReturn("teste");

            doReturn(null).when(mismatchedExceptionMock).getPath();
            doReturn(mismatchedExceptionMock).when(httpExceptionMock).getCause();

            ValidacaoException ex =
                    assertThrows(
                            ValidacaoException.class,
                            () -> handler.handleHttpMessageException(httpExceptionMock));

            assertEquals(EValidacao.CAMPO_INVALIDO_NAO_IDENTIFICADO.getCodigo(), ex.getCodigo());
        }
    }
}
