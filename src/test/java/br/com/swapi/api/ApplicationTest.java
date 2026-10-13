package br.com.swapi.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Sobe o contexto completo, com a api do StarWars simulada, e exercita o fluxo de ponta a ponta
 */
@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTest {

    private static final String SWAPI_RESPONSE = """
            {
              "count": 2,
              "next": null,
              "previous": null,
              "results": [
                {
                  "title": "A New Hope",
                  "episode_id": 4,
                  "opening_crawl": "It is a period of civil war.",
                  "director": "George Lucas",
                  "producer": "Gary Kurtz, Rick McCallum",
                  "release_date": "1977-05-25",
                  "characters": ["https://swapi.dev/api/people/1/"],
                  "planets": ["https://swapi.dev/api/planets/1/"],
                  "starships": ["https://swapi.dev/api/starships/2/"],
                  "vehicles": ["https://swapi.dev/api/vehicles/4/"],
                  "species": ["https://swapi.dev/api/species/1/"],
                  "created": "2014-12-10T14:23:31.880000Z",
                  "edited": "2014-12-20T19:49:45.256000Z",
                  "url": "https://swapi.dev/api/films/1/"
                },
                {
                  "title": "The Empire Strikes Back",
                  "episode_id": 5,
                  "opening_crawl": "It is a dark time for the Rebellion.",
                  "director": "Irvin Kershner",
                  "producer": "Gary Kurtz, Rick McCallum",
                  "release_date": "1980-05-17",
                  "characters": [],
                  "planets": [],
                  "starships": [],
                  "vehicles": [],
                  "species": [],
                  "created": "2014-12-12T11:26:24.656000Z",
                  "edited": "2014-12-15T13:07:53.386000Z",
                  "url": "https://swapi.dev/api/films/2/"
                }
              ]
            }
            """;

    @TestConfiguration
    static class SwapiMockConfig {

        @Bean
        @Primary
        WebClient webClientSwapiMock() {
            return WebClient.builder()
                    .exchangeFunction(request -> Mono.just(ClientResponse.create(HttpStatus.OK)
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(SWAPI_RESPONSE)
                            .build()))
                    .build();
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listAllFilmsLoadedOnStartup() throws Exception {
        mockMvc.perform(get("/api/films"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.results[0].episode_id").value(4))
                .andExpect(jsonPath("$.results[0].release_date").value("1977-05-25"))
                .andExpect(jsonPath("$.results[1].title").value("The Empire Strikes Back"));
    }

    @Test
    void findByIdSuccess() throws Exception {
        mockMvc.perform(get("/api/films/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("A New Hope"))
                .andExpect(jsonPath("$.director").value("George Lucas"))
                .andExpect(jsonPath("$.version").value(1));
    }

    @Test
    void findByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/films/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Sem dados retornados"));
    }

    @Test
    void updateDescriptionIncrementsVersion() throws Exception {
        mockMvc.perform(get("/api/films/5"))
                .andExpect(jsonPath("$.version").value(1));

        putDescription("nova descricao", 2);
        // a mesma descrição não altera a versão
        putDescription("nova descricao", 2);
        putDescription("outra descricao", 3);

        mockMvc.perform(get("/api/films/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.opening_crawl").value("outra descricao"))
                .andExpect(jsonPath("$.version").value(3));

        mockMvc.perform(get("/api/films"))
                .andExpect(jsonPath("$.results[0].version").value(1))
                .andExpect(jsonPath("$.results[1].version").value(3));
    }

    private void putDescription(String description, int expectedVersion) throws Exception {
        mockMvc.perform(put("/api/films/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"" + description + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.detail").value("Sucesso"))
                .andExpect(jsonPath("$.version").value(expectedVersion));
    }

    @Test
    void updateDescriptionNotFound() throws Exception {
        mockMvc.perform(put("/api/films/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"nova descricao\"}"))
                .andExpect(status().isNotFound());
    }

}
