package br.com.swapi.api.datasource.store.mapper;

import br.com.swapi.api.datasource.store.dto.FilmStoreDto;
import br.com.swapi.api.datasource.store.dto.ReturnServiceStoreDto;
import br.com.swapi.api.datasource.store.dto.ReturnServiceStoreDtoMock;
import br.com.swapi.api.entity.Film;
import br.com.swapi.api.entity.FilmMock;
import br.com.swapi.api.entity.ReturnService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FilmStoreMapperTest {

    private final FilmStoreMapper mapper = new FilmStoreMapperImpl();

    @Test
    void entityToDto() {
        Film film = new FilmMock().listAllFilms().results().get(0).updateDescription("nova");

        FilmStoreDto dto = mapper.map(film);

        assertEquals(film.title(), dto.title());
        assertEquals(film.id(), dto.id());
        assertEquals("nova", dto.openingCrawl());
        assertEquals(film.director(), dto.director());
        assertEquals(film.producer(), dto.producer());
        assertEquals(film.releaseDate(), dto.releaseDate());
        assertEquals(film.characters(), dto.characters());
        assertEquals(film.planets(), dto.planets());
        assertEquals(film.starships(), dto.starships());
        assertEquals(film.vehicles(), dto.vehicles());
        assertEquals(film.species(), dto.species());
        assertEquals(film.created(), dto.created());
        assertEquals(film.edited(), dto.edited());
        assertEquals(film.url(), dto.url());
        assertEquals(2, dto.version());
    }

    @Test
    void dtoToEntityKeepsTheVersion() {
        FilmStoreDto dto = mapper.map(new FilmMock().listAllFilms().results().get(0).updateDescription("nova"));

        Film film = mapper.map(dto);

        assertEquals(dto.id(), film.id());
        assertEquals("nova", film.openingCrawl());
        assertEquals(2, film.version());
    }

    @Test
    void roundTripKeepsTheFilm() {
        Film film = new FilmMock().listAllFilms().results().get(0);

        assertEquals(film, mapper.map(mapper.map(film)));
    }

    @Test
    void listToDtoAndBack() {
        ReturnService films = new FilmMock().listAllFilms();

        ReturnServiceStoreDto dto = mapper.map(films);

        assertEquals("3", dto.count());
        assertEquals(3, dto.results().size());
        assertEquals(films, mapper.map(dto));
    }

    @Test
    void dtoListToEntity() {
        ReturnService films = mapper.map(new ReturnServiceStoreDtoMock().listAllFilms());

        assertEquals("3", films.count());
        assertEquals(3, films.results().size());
        assertEquals("Title test 2", films.results().get(1).title());
    }

    @Test
    void mapNull() {
        assertNull(mapper.map((Film) null));
        assertNull(mapper.map((FilmStoreDto) null));
        assertNull(mapper.map((ReturnService) null));
        assertNull(mapper.map((ReturnServiceStoreDto) null));
    }

}
