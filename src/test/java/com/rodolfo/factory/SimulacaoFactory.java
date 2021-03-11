package com.rodolfo.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodolfo.pojo.Simulacao;

import java.io.FileInputStream;
import java.io.IOException;

public class SimulacaoFactory {

    public static Simulacao criarSimulacao() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Simulacao simulacao = objectMapper.readValue(new FileInputStream("src/test/java/resources/requestBody/postV1Simulacao.json"), Simulacao.class);
        return simulacao;
    }

    public static Simulacao criarSimulacaoValida() throws IOException {
        Simulacao simulacaoValida = criarSimulacao();

        return simulacaoValida;
    }

    public static Simulacao criarSimulacaoSemInformacao() throws IOException{
        Simulacao simulacaoSemInformacao = criarSimulacao();

        simulacaoSemInformacao.setEmail("");
        simulacaoSemInformacao.setValor("");
        simulacaoSemInformacao.setParcelas("");

        return  simulacaoSemInformacao;
    }

    public static Simulacao atualizarSimulacaoCpf () throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();

        Simulacao simulacaoAtualizar = objectMapper.readValue(new FileInputStream("src/test/java/resources/requestBody/putV1AtualizarSimulacao.json"), Simulacao.class);
        return simulacaoAtualizar;
    }

    public static Simulacao atualizarSimulacao() throws IOException {
        Simulacao simulacaoValida = atualizarSimulacaoCpf();

        simulacaoValida.setEmail("farleyrodolfo@gmail.com");
        simulacaoValida.setNome("Rodolfo Farley");

        return simulacaoValida;
    }

}