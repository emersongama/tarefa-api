package com.cesmac.tarefa.api.integracao.tarefas;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasItem;

import com.cesmac.tarefa.api.TarefaApiApplication;
import com.cesmac.tarefa.api.integracao.ContainersAbstractIT;
import com.cesmac.tarefa.api.integracao.dto.EntidadeGenericaTestDTO;
import com.cesmac.tarefa.api.shared.dto.GrupoDTO;
import com.cesmac.tarefa.api.shared.dto.TarefaDTO;
import com.cesmac.tarefa.api.shared.uteis.ObjectMapperUtil;
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
public class CadastrarTarefaTest extends ContainersAbstractIT {
    @Autowired private WebApplicationContext webApplicationContextSetup;
    @Autowired private EntityManager em;

    @BeforeEach
    void init() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContextSetup);
    }

    @Test
    @DisplayName(
            "Deve testar o cadastro de tarefa, resultado em sucesso, retornando a tarefa cadastrado")
    @Sql({"/seeds/scripts/base.sql", "/seeds/scripts/insert_grupos.sql"})
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
    @DisplayName("Deve retornar validação de campos obrigatorios no cadastro de tarefa")
    public void deveValidarCamposVaziosCadastroDeTarefa() {
        TarefaDTO request = new TarefaDTO();
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(ObjectMapperUtil.asJsonString(request))
                .when()
                .post("/api/tarefa")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("erros", hasSize(2))
                .body("erros.mensagem", hasItem("O campo titulo deve ser informado;"))
                .body("erros.mensagem", hasItem("O campo descricao deve ser informado;"))
                .extract()
                .response()
                .prettyPrint();
    }

    @Test
    @DisplayName("Deve retornar validação de campo invalido no cadastro de tarefa")
    public void deveValidarCampoInvalidoCadastroDeTarefa() {
        EntidadeGenericaTestDTO request = new EntidadeGenericaTestDTO();
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(ObjectMapperUtil.asJsonString(request))
                .when()
                .post("/api/tarefa")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("erros", hasSize(1))
                .body("erros.mensagem", hasItem("O campo nome não é válido."))
                .extract()
                .response()
                .prettyPrint();
    }

    private TarefaDTO tarefaRequest() {
        return TarefaDTO.builder()
                .titulo("Tarefa Nova")
                .descricao("Desc Teste Nova")
                .grupo(GrupoDTO.builder().id(1L).build())
                .build();
    }
}
