package com.cesmac.tarefa.api;

import com.cesmac.tarefa.api.configuration.TarefaApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({TarefaApiProperties.class})
public class TarefaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TarefaApiApplication.class, args);
    }
}
