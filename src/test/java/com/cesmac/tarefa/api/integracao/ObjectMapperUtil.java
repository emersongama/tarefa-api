package com.cesmac.tarefa.api.integracao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

public class ObjectMapperUtil {
    private static final String MSG_ERRO_CONVERSAO_BYTES_EM_OBJETO =
            "Erro ao converter bytes em um objeto do tipo %s";

    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        simpleDateFormat.setLenient(false);
        objectMapper.setDateFormat(simpleDateFormat);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_INDEX);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter Objeto em String");
        }
    }

    public <T> T parseJsonByteArrayToObject(byte[] byteObjeto, Class<T> tipoObject) {
        try {
            return objectMapper().readValue(new String(byteObjeto), tipoObject);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(
                    String.format(MSG_ERRO_CONVERSAO_BYTES_EM_OBJETO, tipoObject.getName()), e);
        }
    }
}
