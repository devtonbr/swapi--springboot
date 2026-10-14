package br.com.swapi.api.datasource.store.aspects;

import br.com.swapi.api.datasource.store.aspects.ReturnStoreElement;
import br.com.swapi.api.entity.Film;
import br.com.swapi.api.entity.FilmMock;
import br.com.swapi.api.exception.MemoryStoreException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.swapi.api.constants.Constants.NOT_FOUND_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReturnStoreElementTest {

    @InjectMocks
    private ReturnStoreElement aspect;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Test
    void returnsTheObjectWhenNotNull() throws Throwable {
        Film film = new FilmMock().listAllFilms().results().get(0);
        when(joinPoint.proceed()).thenReturn(film);

        assertSame(film, aspect.getNullReturn(joinPoint));
    }

    @Test
    void throwsDaoExceptionWhenNull() throws Throwable {
        when(joinPoint.proceed()).thenReturn(null);

        MemoryStoreException exception = assertThrows(MemoryStoreException.class, () -> aspect.getNullReturn(joinPoint));
        assertEquals(NOT_FOUND_MESSAGE, exception.getMessage());
    }

}
