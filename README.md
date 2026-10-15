# SWAPI - POC (SPRINGBOOT)

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

A configuração é feita por variáveis de ambiente:

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

Os filmes são carregados da api do StarWars na subida da aplicação e ficam em memória.

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

O deploy usa o plano gratuito do [Render](https://render.com), descrito em `render.yaml`. O passo a passo está em "Configurando o Render".

Sem o secret `RENDER_DEPLOY_HOOK_URL`, o job de deploy é ignorado e os demais continuam rodando. Na cloud a aplicação escuta na porta da variável `PORT` (padrão local: 5000) e o actuator não fica exposto. No plano gratuito o serviço hiberna sem uso; a primeira chamada depois disso demora, e o estado em memória volta ao original.

## Configurando o Render

O Render não precisa ser instalado: é um serviço de cloud, e tudo é feito pelo navegador. O serviço é criado a partir do arquivo `render.yaml` deste repositório.

### 1. Criar a conta

1. Acesse [render.com](https://render.com) e clique em **Get Started**.
2. Entre com a conta do GitHub. O plano gratuito não pede cartão de crédito.

### 2. Criar o serviço pelo Blueprint

1. No painel ([dashboard.render.com](https://dashboard.render.com)), clique em **New > Blueprint**.
2. Autorize o Render a acessar o GitHub, se for pedido, e escolha este repositório.
3. Dê um nome ao Blueprint, mantenha o branch `main` e confirme. O Render lê o `render.yaml` e mostra o serviço web `swapi-springboot`, no plano **Free**.
4. Clique em **Apply** (ou **Deploy Blueprint**) e aguarde. O primeiro build da imagem Docker leva alguns minutos.

### 3. Conferir se está no ar

1. Abra o serviço `swapi-springboot`. A URL pública aparece no topo da página; se o nome já estiver em uso, o Render acrescenta um sufixo ao endereço.
2. Na aba **Events**, o deploy deve aparecer como **Live**.
3. Teste no navegador, trocando o endereço pelo do seu serviço:

```
https://swapi-springboot.onrender.com/sw-springboot/api/films
https://swapi-springboot.onrender.com/sw-springboot/swagger-ui/index.html
```

### 4. Ligar o deploy automático pelo GitHub Actions

O `render.yaml` deixa o deploy automático do Render desligado, para que a publicação só aconteça depois dos testes do pipeline.

1. No serviço, em **Settings > Deploy Hook**, copie a URL.
2. No GitHub, em **Settings > Secrets and variables > Actions > New repository secret**, crie o secret `RENDER_DEPLOY_HOOK_URL` com essa URL.
3. A partir do próximo push em `main`, o job "Deploy no Render" mostra a mensagem "Deploy solicitado ao Render." e o novo deploy aparece na aba **Events** do serviço.

### Custos e limites do plano gratuito

- O plano gratuito não gera cobrança. Confira a página **Billing** do workspace para ver o consumo e se há cartão cadastrado.
- O serviço hiberna depois de alguns minutos sem acesso. A primeira chamada seguinte demora perto de um minuto, e as descrições alteradas voltam ao original.
- A API fica pública: qualquer pessoa com a URL consegue alterar as descrições pelo PUT.

### Removendo a aplicação

1. No serviço, em **Settings**, role até o fim da página e clique em **Delete Web Service**.
2. Em **Blueprints**, abra o Blueprint, vá em **Settings** e clique em **Delete Blueprint**. Sem isso, uma nova sincronização recria o serviço.
3. No GitHub, apague o secret `RENDER_DEPLOY_HOOK_URL`, senão o próximo push falha ao chamar um hook que não existe mais.

## Autores

* **Clayton Morais de Oliveira** 
