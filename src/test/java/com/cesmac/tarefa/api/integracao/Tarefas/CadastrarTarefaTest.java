package com.cesmac.tarefa.api.integracao.Tarefas;

import com.cesmac.tarefa.api.TarefaApiApplication;
import com.cesmac.tarefa.api.integracao.ContainersAbstractIT;
import com.cesmac.tarefa.api.integracao.ObjectMapperUtil;
import com.cesmac.tarefa.api.shared.dto.TarefaDTO;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import javax.persistence.EntityManager;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Tag("integracao")
@SpringBootTest(
        classes = TarefaApiApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CadastrarTarefaTest extends ContainersAbstractIT {
    @Autowired
    private WebApplicationContext webApplicationContextSetup;
    @Autowired private EntityManager em;

    @BeforeEach
    void init() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContextSetup);
    }
    @Test
    @DisplayName(
            "Deve testar o cadastro de tarefa, resultado em sucesso, retornando a tarefa cadastrado")
    public void deveriaCadastrarNovaTarefaComSucesso() {
        TarefaDTO request = tarefaRequest();
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(ObjectMapperUtil.asJsonString(request))
                .when()
                .post("/api/tarefa")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("titulo", equalTo(request.getTitulo()))
                .body("descricao", equalTo(request.getDescricao()))
                .extract()
                .response()
                .prettyPrint();
    }

    @Test
    @DisplayName(
            "Deve testar o cadastro de tarefa, resultado em sucesso, retornando a tarefa cadastrado")
    public void DeveriaCadastrarNovoUsuarioComSucesso() {
        TarefaDTO request = new TarefaDTO();
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(ObjectMapperUtil.asJsonString(request))
                .when()
                .post("/api/tarefa")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("titulo", equalTo(request.getTitulo()))
                .body("descricao", equalTo(request.getDescricao()))
                .extract()
                .response()
                .prettyPrint();
    }

    private TarefaDTO tarefaRequest() {
        return TarefaDTO.builder().titulo("Titulo").descricao("Nova Desc").build();
    }
}
