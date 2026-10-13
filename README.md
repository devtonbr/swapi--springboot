# SWAPI - POC (EXEC)

Projeto conceito para utilização de Serviço Rest com Spring Boot

## Iniciando o Projeto

O projeto contém alguns exemplos de serviço REST. O projeto já está configurado em modo standalone

### Pré requisitos

```
Java 17;

Spring Boot na versão 3.4.5;

Eclipse ou Intellij

Postman para Testes : (https://www.postman.com/downloads/)
```

## Rodando os Testes

Execute os testes com Maven:

```
mvn test
```

## Automação BDD no Postman

A collection está na raiz do repositório, no formato Postman v2.1 (o Bruno também importa esse arquivo).

```
SWAPI - API - LOCAL.postman_collection.json
```

Ela tem duas pastas:

- **API**: todos os endpoints da aplicação, sem testes, para uso manual.
- **Teste Automatizado**: os cenários BDD, com asserts.

A pasta **Teste Automatizado** está organizada assim:

- cada pasta é uma **Funcionalidade**;
- cada subpasta é um **Cenário**, com o texto Gherkin na descrição;
- cada requisição é um passo (**Dado**, **Quando** ou **E**), e os testes da requisição são os **Então**.

Funcionalidades cobertas: consulta de filmes, alteração da descrição, controle de versão e documentação e saúde da aplicação (14 cenários).

Para executar:

1. Suba a aplicação (veja "Rodando local").
2. Importe a collection no Postman.
3. Abra a pasta **Teste Automatizado**, uma funcionalidade ou um cenário e clique em **Run**.

Os cenários são independentes entre si e podem ser repetidos sem reiniciar a aplicação. Se necessário, ajuste as variáveis `baseUrl`, `actuatorUrl`, `filmId` e `otherFilmId` da collection.

## OpenAPI

A especificação e a interface Swagger UI estão disponíveis nos endpoints abaixo.

```
GET:/sw-springboot/v3/api-docs
GET:/sw-springboot/swagger-ui/index.html
```

## Rodando local

Inicie com a classe Application.java

## Variáveis de ambiente

A aplicação não usa config-server. A configuração é feita por variáveis de ambiente:

```
SWAPI_ENDPOINT : endpoint da api de filmes do StarWars (padrão: https://swapi.py4e.com/api/films/)
SPRING_PROFILES_ACTIVE : profiles do Spring (padrão: dev). Inclua jsonlog para logs em formato json
OTEL_EXPORTER_OTLP_TRACING_ENDPOINT : endpoint do coletor de tracing
```

## Endpoints

```
GET /sw-springboot/api/films       : lista os filmes, com a versão de cada um
GET /sw-springboot/api/films/{id}  : detalha um filme
PUT /sw-springboot/api/films/{id}  : altera a descrição de um filme
```

O `{id}` é o número do episódio (`episode_id`), não o id do SWAPI: `/api/films/4` é "A New Hope" (episódio IV).

## Logs em formato json

Com o profile `jsonlog` ativo, cada evento de log sai em uma linha json:

```
SPRING_PROFILES_ACTIVE=dev,jsonlog java -jar swapispringboot-0.0.1-SNAPSHOT.jar
```

```
{"timestamp":"2026-10-13T17:42:20.752","service":"swapispringboot","level":"INFO","trace":"","span":"","thread":"main","class":"br.com.swapi.api.Application","message":"Iniciando o a Aplicação - SWAPI","exception":""}
```

Os campos `trace` e `span` são preenchidos durante o atendimento das requisições, e `exception` traz o stack trace quando houver. Sem o profile, o log sai no formato padrão do Spring Boot. A imagem Docker já sobe com `jsonlog`. A configuração está em `src/main/resources/logback-spring.xml`.

## Estado em memória

Os filmes são carregados da api do StarWars na subida da aplicação e ficam em memória. As descrições alteradas pelo PUT se perdem ao reiniciar.

## Controle de versão

Cada filme tem um campo `version`, que começa em 1 na carga e é incrementado em 1 toda vez que a descrição é alterada pelo PUT. Enviar a mesma descrição não altera a versão. A versão é devolvida na resposta do PUT e nas consultas.

## Deploy

Basta executar o comando Maven:

```
mvn clean package
```

Para rodar, vá na pasta target onde tem o artefato gerado e execute o comando:

```
java -jar swapispringboot-0.0.1-SNAPSHOT.jar
```


## CI/CD e deploy na cloud

O workflow `.github/workflows/ci-cd.yml` roda no GitHub Actions a cada push ou pull request para `main`:

1. **Build e testes**: `mvn verify` com Java 17.
2. **Imagem Docker e teste de fumaça**: constrói a imagem, sobe o container e confere os endpoints.
3. **Deploy no Render**: só em push para `main`, dispara o deploy pelo deploy hook.

O deploy usa o plano gratuito do [Render](https://render.com), descrito em `render.yaml`. Para ativar:

1. No Render, crie um **Blueprint** apontando para este repositório (ele lê o `render.yaml` e cria o serviço `swapi-springboot`).
2. Em **Settings > Deploy Hook** do serviço, copie a URL.
3. No GitHub, em **Settings > Secrets and variables > Actions**, crie o secret `RENDER_DEPLOY_HOOK_URL` com essa URL.

Sem o secret, o job de deploy é ignorado e os demais continuam rodando. Na cloud a aplicação escuta na porta da variável `PORT` (padrão local: 5000) e o actuator não fica exposto. No plano gratuito o serviço hiberna sem uso; a primeira chamada depois disso demora, e o estado em memória volta ao original.

## Autores

* **Clayton Morais de Oliveira** 
