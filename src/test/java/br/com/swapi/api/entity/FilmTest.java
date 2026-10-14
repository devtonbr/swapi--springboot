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
        film = new FilmMock().listAllFilms().results().get(0);
    }

    @Test
    void newFilmStartsAtVersionOne() {
        assertEquals(1, film.version());
    }

    @Test
    void filmWithoutVersionStartsAtVersionOne() {
        Film semVersao = new Film("", 1, "", "", "", "", null, null, null, null, null, "", "", "", null);

        assertEquals(1, semVersao.version());
    }

    @Test
    void filmKeepsTheGivenVersion() {
        Film versaoCinco = new Film("", 1, "", "", "", "", null, null, null, null, null, "", "", "", 5);

        assertEquals(5, versaoCinco.version());
        assertEquals(6, versaoCinco.updateDescription("nova").version());
    }

    @Test
    void updateDescriptionIncrementsVersion() {
        Film updated = film.updateDescription("nova descriçao");

        assertNotSame(film, updated);
        assertEquals("nova descriçao", updated.openingCrawl());
        assertEquals(2, updated.version());

        // os demais campos são preservados
        assertEquals(film.id(), updated.id());
        assertEquals(film.title(), updated.title());
        assertEquals(film.director(), updated.director());
        assertEquals(film.producer(), updated.producer());
        assertEquals(film.releaseDate(), updated.releaseDate());
        assertEquals(film.characters(), updated.characters());
        assertEquals(film.planets(), updated.planets());
        assertEquals(film.starships(), updated.starships());
        assertEquals(film.vehicles(), updated.vehicles());
        assertEquals(film.species(), updated.species());
        assertEquals(film.created(), updated.created());
        assertEquals(film.edited(), updated.edited());
        assertEquals(film.url(), updated.url());

        // o objeto original não é alterado
        assertEquals("Description test 1", film.openingCrawl());
        assertEquals(1, film.version());
    }

    @Test
    void updateWithSameDescriptionKeepsVersion() {
        Film updated = film.updateDescription("Description test 1");

        assertSame(film, updated);
        assertEquals(1, updated.version());
    }

    @Test
    void successiveUpdatesIncrementOneByOne() {
        Film second = film.updateDescription("descriçao 2");
        Film third = second.updateDescription("descriçao 3");
        Film sameAsThird = third.updateDescription("descriçao 3");
        Film fourth = third.updateDescription("Description test 1");

        assertEquals(2, second.version());
        assertEquals(3, third.version());
        assertSame(third, sameAsThird);
        // voltar ao texto original também é uma alteração
        assertEquals(4, fourth.version());
    }

}
