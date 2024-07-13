package com.cesmac.tarefa.api;

import com.cesmac.tarefa.api.integracao.ContainersAbstractIT;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(
        classes = TarefaApiApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class TarefaApiApplicationTests extends ContainersAbstractIT {

    @Autowired private WebApplicationContext webApplicationContextSetup;

    @BeforeEach
    void init() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContextSetup);
    }

    @Test
    void contextLoads() {}
}
