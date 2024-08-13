package com.cesmac.tarefa.api.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "tarefa-api", ignoreUnknownFields = false)
public class TarefaApiProperties {

    private String segredoBase64;
}
