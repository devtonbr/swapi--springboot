package br.com.swapi.api.controller;

import br.com.swapi.api.config.ExceptionHandler;
import br.com.swapi.api.controller.mapper.FilmsMapperImpl;
import br.com.swapi.api.entity.FilmMock;
import br.com.swapi.api.entity.InputParameter;
import br.com.swapi.api.entity.ReturnService;
import br.com.swapi.api.exception.MemoryStoreException;
import br.com.swapi.api.usecase.FilmUseCase;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static br.com.swapi.api.constants.Constants.NOT_FOUND_MESSAGE;
import static br.com.swapi.api.constants.Constants.SUCCESS_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FilmsApiImplTest {

    private MockMvc mockMvc;

    @Mock
    private FilmUseCase filmsUseCase;

    @Spy
    private FilmsMapperImpl filmsMapper;

    @InjectMocks
    private FilmsApiImpl controller;

    private final ReturnService films = new FilmMock().listAllFilms();

    @BeforeEach
    void setup() {
        // datas em formato ISO, como na configuração padrão do Spring Boot
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(
                Jackson2ObjectMapperBuilder.json()
                        .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                        .build());

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ExceptionHandler())
                .setMessageConverters(jsonConverter)
                .build();
    }

    @Test
    void listAllFilmsSuccess() throws Exception {
        when(filmsUseCase.listAll()).thenReturn(films);

        mockMvc.perform(get("/api/films"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(3))
                .andExpect(jsonPath("$.results.length()").value(3))
                .andExpect(jsonPath("$.results[0].episode_id").value(1))
                .andExpect(jsonPath("$.results[0].version").value(1))
                .andExpect(jsonPath("$.results[0].opening_crawl").value("Description test 1"))
                .andExpect(jsonPath("$.results[0].release_date").value("2021-02-10"));

        verify(filmsMapper).map(films);
    }

    @Test
    void findByIdSuccess() throws Exception {
        when(filmsUseCase.findById(1)).thenReturn(films.results().get(0));

        mockMvc.perform(get("/api/films/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.episode_id").value(1))
                .andExpect(jsonPath("$.director").value("Director test 1"))
                .andExpect(jsonPath("$.version").value(1))
                .andExpect(jsonPath("$.characters[0]").value("characters 1"));

        verify(filmsMapper).map(films.results().get(0));
    }

    @Test
    void findByIdNotFound() throws Exception {
        when(filmsUseCase.findById(99)).thenThrow(new MemoryStoreException(NOT_FOUND_MESSAGE));

        mockMvc.perform(get("/api/films/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value(NOT_FOUND_MESSAGE));
    }

    @Test
    void findByIdUnexpectedError() throws Exception {
        when(filmsUseCase.findById(1)).thenThrow(new IllegalStateException("erro"));

        mockMvc.perform(get("/api/films/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.detail").exists());
    }

    @Test
    void updateDescriptionSuccess() throws Exception {
        when(filmsUseCase.updateDescription(eq(1), any(InputParameter.class)))
                .thenReturn(films.results().get(0).updateDescription("nova descricao"));

        mockMvc.perform(put("/api/films/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"nova descricao\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.detail").value(SUCCESS_MESSAGE))
                .andExpect(jsonPath("$.version").value(2));

        ArgumentCaptor<InputParameter> input = ArgumentCaptor.forClass(InputParameter.class);
        verify(filmsUseCase).updateDescription(eq(1), input.capture());
        assertEquals("nova descricao", input.getValue().description());
    }

    @Test
    void updateDescriptionWithoutBody() throws Exception {
        mockMvc.perform(put("/api/films/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(filmsUseCase, never()).updateDescription(any(), any());
    }

    @Test
    void updateDescriptionWithoutDescription() throws Exception {
        mockMvc.perform(put("/api/films/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());

        verify(filmsUseCase, never()).updateDescription(any(), any());
    }

}
