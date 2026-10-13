package br.com.swapi.api.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

class FilmTest {

    private Film film;

    @BeforeEach
    void init() {
        film = new FilmMock().listAllFilms().getResults().get(0);
    }

    @Test
    void newFilmStartsAtVersionOne() {
        assertEquals(1, film.getVersion());
    }

    @Test
    void updateDescriptionIncrementsVersion() {
        Film updated = film.updateDescription("nova descriçao");

        assertNotSame(film, updated);
        assertEquals("nova descriçao", updated.getOpeningCrawl());
        assertEquals(2, updated.getVersion());

        // os demais campos são preservados
        assertEquals(film.getId(), updated.getId());
        assertEquals(film.getTitle(), updated.getTitle());
        assertEquals(film.getDirector(), updated.getDirector());
        assertEquals(film.getProducer(), updated.getProducer());
        assertEquals(film.getReleaseDate(), updated.getReleaseDate());
        assertEquals(film.getCharacters(), updated.getCharacters());
        assertEquals(film.getPlanets(), updated.getPlanets());
        assertEquals(film.getStarships(), updated.getStarships());
        assertEquals(film.getVehicles(), updated.getVehicles());
        assertEquals(film.getSpecies(), updated.getSpecies());
        assertEquals(film.getCreated(), updated.getCreated());
        assertEquals(film.getEdited(), updated.getEdited());
        assertEquals(film.getUrl(), updated.getUrl());

        // o objeto original não é alterado
        assertEquals("Description test 1", film.getOpeningCrawl());
        assertEquals(1, film.getVersion());
    }

    @Test
    void updateWithSameDescriptionKeepsVersion() {
        Film updated = film.updateDescription("Description test 1");

        assertSame(film, updated);
        assertEquals(1, updated.getVersion());
    }

    @Test
    void successiveUpdatesIncrementOneByOne() {
        Film second = film.updateDescription("descriçao 2");
        Film third = second.updateDescription("descriçao 3");
        Film sameAsThird = third.updateDescription("descriçao 3");
        Film fourth = third.updateDescription("Description test 1");

        assertEquals(2, second.getVersion());
        assertEquals(3, third.getVersion());
        assertSame(third, sameAsThird);
        // voltar ao texto original também é uma alteração
        assertEquals(4, fourth.getVersion());
    }

}
