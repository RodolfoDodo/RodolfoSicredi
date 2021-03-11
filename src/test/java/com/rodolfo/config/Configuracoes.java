package com.rodolfo.config;


import org.aeonbits.owner.Config;

@Config.Sources({"file:src/test/java/resources/properties/test.properties"})
public interface Configuracoes extends Config {

    @Key("baseURI")
    String baseURI();

    @Key("port")
    int port();

    @Key("basePath")
    String basePath();

    @Key("cpf")
    String cpf();
}
