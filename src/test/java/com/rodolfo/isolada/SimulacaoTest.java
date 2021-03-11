package com.rodolfo.isolada;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

import com.rodolfo.config.Configuracoes;
import com.rodolfo.factory.SimulacaoFactory;
import com.rodolfo.pojo.Simulacao;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;


import io.restassured.http.ContentType;

public class SimulacaoTest {

     static String cpf = "12364697674";

     static String cpfNaoEncontrado = "999999999";

     static  String id = "12";

     static  String cpfRestricao = "55856777050";

    @Before
    public void setup() {
        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);
        // configuração para acessar a api

        baseURI = configuracoes.baseURI();
        port = configuracoes.port();
        basePath = configuracoes.basePath();
    }

    @Test
    public void consultaCpfRestricaoTest(){
        given()
            .contentType(ContentType.JSON)
                //.body("")
        .when()
            .get("/v1/restricoes/" + cpfRestricao)
        .then()
            .log()
                .all()
                    .assertThat()
                    .statusCode(200)
                    .body("mensagem", equalTo("O CPF 55856777050 tem problema"));
    }

    @Test
    public void retornarTodasSimulacoesTest() throws IOException{
        given()
            .contentType(ContentType.JSON)
            //.body("")
        .when()
            .get("/v1/simulacoes")
        .then()
            .log()
            .all()
            .assertThat()
                .statusCode(200);
    }

    @Test
    public void insereSimulacaoTest() throws IOException {
        Simulacao simulacaoValida = SimulacaoFactory.criarSimulacaoValida();

            given()
                .contentType(ContentType.JSON)
                .body(simulacaoValida)
            .when()
                .post("/v1/simulacoes")
            .then()
                .log()
                    .all()
                    .assertThat()
                    .statusCode(201)
                         .body("nome", equalToIgnoringCase("Rodolfo farley"))
                         .body("valor", equalTo(1200))
                        // .body("cpf", equalTo("12214697674"))
                         .body("email", equalTo("email@email.com"))
                         .body("valor", equalTo(1200))
                         .body("parcelas", equalTo(3));

    }

    @Test
    public void insereSimulacaoSemInformacaoTest() throws IOException{
        Simulacao simulacaoValida = SimulacaoFactory.criarSimulacaoSemInformacao();

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoValida)
        .when()
            .post("/v1/simulacoes")
        .then()
            .log()
                .all()
                    .assertThat()
                    .statusCode(400)
                    .body("erros.parcelas", equalTo("Parcelas não pode ser vazio"))
                    .body("erros.valor", equalTo("Valor não pode ser vazio"))
                    .body("erros.email", equalTo("E-mail deve ser um e-mail válido"));
    }

    @Test
    public void insereSimulacaoCpfDuplicadoTest() throws IOException{
        Simulacao simulacaoValida = SimulacaoFactory.criarSimulacaoValida();

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoValida)
        .when()
            .post("/v1/simulacoes")
        .then()
            .log()
            .all()
                .assertThat()
                 .statusCode(400)
                    .body("mensagem", equalToIgnoringCase("CPF duplicado"));
    }

    @Test
    public void retornarSimulacaoCPFTest() throws IOException{
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/v1/simulacoes/" + cpf)
        .then()
            .log()
            .all()
                .assertThat()
                    .statusCode(200)
                        .body("nome", equalToIgnoringCase("Rodolfo Farley"))
                        .body("valor", equalTo(1200.00F))
                        .body("cpf", equalTo("12364697674"))
                        .body("email", equalTo("email@email.com"))
                        .body("parcelas", equalTo(3));
    }

    @Test
    public void retornarSimulacaoCpfNaoEncontradoTest(){
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/v1/simulacoes/" + cpfNaoEncontrado)
        .then()
            .log()
                .all()
                    .assertThat()
                    .statusCode(404)
                    .body("mensagem", equalTo("CPF 999999999 não encontrado"));
    }

    @Test
    public void atualizarSimulacaoCpfTest() throws IOException{
        Simulacao simulacaoCpf = SimulacaoFactory.atualizarSimulacao();

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoCpf)
        .when()
            .put("/v1/simulacoes/" + cpf)
        .then()
            .log()
                .all()
                    .assertThat()
                    .statusCode(200)
                    .body("nome", equalToIgnoringCase("Rodolfo Farley"))
                     .body("email", equalTo("farleyrodolfo@gmail.com"));
    }

    @Test
    public void atualizarSimulacaoCpfNaoEncontradoTest() throws IOException{
        Simulacao simulacaoCpf = SimulacaoFactory.atualizarSimulacao();

        given()
            .contentType(ContentType.JSON)
            .body(simulacaoCpf)

        .when()
            .put("/v1/simulacoes/" + cpfNaoEncontrado)
        .then()
            .log()
                .all()
                    .assertThat()
                    .statusCode(404)
                    .body("mensagem", equalToIgnoringCase("CPF 999999999 não encontrado"));
    }

    @Test
    public void removerSimulacaoTest(){
        given()
            .contentType(ContentType.JSON)
        .when()
            .delete("/v1/simulacoes/" + id)
        .then()
            .log()
                .all()
                    .assertThat()
                    .statusCode(200);
    }
}
