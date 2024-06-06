package com.cesmac.tarefa.api.integracao.tarefas;

import static org.hamcrest.Matchers.*;

import com.cesmac.tarefa.api.TarefaApiApplication;
import com.cesmac.tarefa.api.integracao.ContainersAbstractIT;
import com.cesmac.tarefa.api.integracao.dto.EntidadeGenericaTestDTO;
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
public class EditarTarefaTest extends ContainersAbstractIT {
    @Autowired private WebApplicationContext webApplicationContextSetup;
    @Autowired private EntityManager em;

    @BeforeEach
    void init() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContextSetup);
    }

    @Test
    @DisplayName("Deve editar uma tarefa, resultado em sucesso, retornando a tarefa editada")
    @Sql({"/seeds/scripts/base.sql"})
    @Sql({"/seeds/scripts/insert_tarefas.sql"})
    public void deveriaEditarNovaTarefaComSucesso() {
        TarefaDTO request = tarefaRequest();
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(ObjectMapperUtil.asJsonString(request))
                .when()
                .put("/api/tarefa/1")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .body("titulo", equalTo(request.getTitulo()))
                .body("descricao", equalTo(request.getDescricao()))
                .extract()
                .response()
                .prettyPrint();
    }

    @Test
    @DisplayName("Deve retornar validação de campos obrigatorios na edição de tarefa")
    public void deveValidarCamposVaziosEdicaoDeTarefa() {
        TarefaDTO request = new TarefaDTO();
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(ObjectMapperUtil.asJsonString(request))
                .when()
                .put("/api/tarefa/1")
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
    @DisplayName("Deve retornar validação de campo invalido na edição de tarefa")
    public void deveValidarCampoInvalidoEdicaoDeTarefa() {
        EntidadeGenericaTestDTO request = new EntidadeGenericaTestDTO();
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(ObjectMapperUtil.asJsonString(request))
                .when()
                .put("/api/tarefa/1")
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

    @Test
    @DisplayName("Deve retornar validação de tarefa não localizada na edição de tarefa")
    @Sql({"/seeds/scripts/base.sql"})
    @Sql({"/seeds/scripts/insert_tarefas.sql"})
    public void deveValidarTarefaNaoLocalizadaNaEdicaoDeTarefa() {
        TarefaDTO request = tarefaRequest();
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(ObjectMapperUtil.asJsonString(request))
                .when()
                .put("/api/tarefa/100")
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

    private TarefaDTO tarefaRequest() {
        return TarefaDTO.builder().titulo("Tarefa Nova").descricao("Desc Teste Nova").build();
    }
}
