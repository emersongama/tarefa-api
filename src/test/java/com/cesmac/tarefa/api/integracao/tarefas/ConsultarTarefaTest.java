package com.cesmac.tarefa.api.integracao.tarefas;

import static org.hamcrest.Matchers.*;

import com.cesmac.tarefa.api.TarefaApiApplication;
import com.cesmac.tarefa.api.integracao.ContainersAbstractIT;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.context.WebApplicationContext;

@Tag("integracao")
@SpringBootTest(
        classes = TarefaApiApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ConsultarTarefaTest extends ContainersAbstractIT {
    @Autowired private WebApplicationContext webApplicationContextSetup;
    @Autowired private EntityManager em;

    @BeforeEach
    void init() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContextSetup);
    }

    @Test
    @DisplayName("Deve busca a tarefa por id")
    @Sql({
        "/seeds/scripts/base.sql",
        "/seeds/scripts/insert_grupos.sql",
        "/seeds/scripts/insert_tarefas.sql"
    })
    public void deveriaBuscarTarefaPorIdComSucesso() {
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/tarefa/1")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .body("titulo", equalTo("Tarefa 1"))
                .body("descricao", equalTo("Desc Teste 1"))
                .extract()
                .response()
                .prettyPrint();
    }

    @Test
    @DisplayName("Deve retornar validação de tarefa não localizada na busca de tarefa por id")
    @Sql({
        "/seeds/scripts/base.sql",
        "/seeds/scripts/insert_grupos.sql",
        "/seeds/scripts/insert_tarefas.sql"
    })
    public void deveValidarTarefaNaoLocalizadaAoBuscarTarefaPorID() {
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/tarefa/100")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("erros", hasSize(1))
                .body("erros.mensagem", hasItem("Tarefa não encontrada."))
                .extract()
                .response()
                .prettyPrint();
    }
}
