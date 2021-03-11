Feature: Simulacao de credito Sincredi

  Realizar os testes funcionais da Api de Simulações

  Background: Acesso a Api na minha maquina local
    Given dado que acesso a API localmente pelo endereco "http://localhost:8080/swagger-ui.html"


  Scenario: Consulta uma restricao
    Given que acesso a URI da api do metado get
    When informo o CPF "60094146012"
    Then tenho a resposta "O CPF 60094146012 possui restrição"

  Scenario: Retornar todas as Simulacoes
    Given que acesso a URI da api do metado get
    Then todas as simulacoes seram retornadas

  Scenario: Insere uma nova Simulacao
    Given que acesso a URI da API do metado get
    When informo todas as informacoes no json
      | nome    | cpf         | email              | valor | parcelas | seguro |
      | Rodolfo | 12814762547 | farlerrr@gmail.com | 1200  | 3        | true   |
    Then a simulacao e realizada com sucesso

  Scenario: Insere uma simulacao faltando informacoes
    Given que acesso a URI da API do metado post
    When informo todas as informacoes no json
      | nome    | cpf         | email              | valor | parcelas | seguro |
      | Rodolfo | 12814762547 |                    | 1200  | 3        | true   |
    Then uma mensagem e informada "Falta de informações"

  Scenario: Insere uma simulacao com CPF cadasdastrado
    Given que acesso a URI da API do metado post
    When informo todas as informacoes no json
      | nome    | cpf         | email              | valor | parcelas | seguro |
      | Rodolfo | 12814762547 | farlerrr@gmail.com | 1200  | 3        | true   |
    Then uma mensagem e informada "cpf duplicado"

  Scenario: Retornar uma simulacao atraves do CPF
    Given que acesso a URI da api do metado put
    When informo o CPF "60094146012"
    Then tenho a resposta "simulacao encontrada"

  Scenario: Retornar uma simulacao atraves do CPF nao encontrado
    Given que acesso a URI da api do metado put
    When informo o CPF "60094146012"
    Then tenho a resposta "Simulação não encontrada"


  Scenario: Excluir uma simulacao atraves do ID
    Given que acesso a URI da api do metado delete
    When informo um ID "17"
    Then tenho a respota "Simulação removida com sucesso"