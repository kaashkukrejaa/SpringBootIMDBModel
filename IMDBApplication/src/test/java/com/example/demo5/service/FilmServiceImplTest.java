package com.example.demo5.service;

import com.example.demo5.dao.FilmRepository;
import com.example.demo5.entity.Film;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FilmServiceImplTest {

    @InjectMocks
    private FilmServiceImpl filmService;

    @Mock
    private FilmRepository filmRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // given
        Film film1 = new Film();
        film1.setId(1L);
        film1.setName("Film 1");
        film1.setGenre("Genre 1");
        film1.setDirector("Director 1");
        film1.setAverageRating(4.5f);

        Film film2 = new Film();
        film2.setId(2L);
        film2.setName("Film 2");
        film2.setGenre("Genre 2");
        film2.setDirector("Director 2");
        film2.setAverageRating(3.5f);

        when(filmRepository.findAll()).thenReturn(Arrays.asList(film1, film2));

        // when
        List<Film> films = filmService.findAll();

        // then
        assertEquals(2, films.size());
        assertEquals(film1, films.get(0));
        assertEquals(film2, films.get(1));

        verify(filmRepository, times(1)).findAll();
    }

    // other test methods

    @Test
    void testFindByName() {
        // given
        Film film1 = new Film();
        film1.setId(1L);
        film1.setName("Film 1");
        film1.setGenre("Genre 1");
        film1.setDirector("Director 1");
        film1.setAverageRating(4.5f);

        when(filmRepository.findByName("Film 1")).thenReturn(Arrays.asList(film1));

        // when
        List<Film> films = filmService.findByName("Film 1");

        // then
        assertEquals(1, films.size());
        assertEquals(film1, films.get(0));

        verify(filmRepository, times(1)).findByName("Film 1");
    }

    @Test
    void testFindByName_notFound() {
        // given
        when(filmRepository.findByName("Film 3")).thenThrow(new EntityNotFoundException("Film not found with name: Film 3"));

        // when
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> filmService.findByName("Film 3"));

        // then
        assertEquals("Film not found with name: Film 3", exception.getMessage());

        verify(filmRepository, times(1)).findByName("Film 3");
    }

    @Test
    void save() {
        // given
        Film film = new Film();
        film.setName("Film 3");
        film.setGenre("Genre 3");
        film.setDirector("Director 3");
        film.setAverageRating(4.0f);

        Film savedFilm = new Film();
        savedFilm.setId(3L);
        savedFilm.setName("Film 3");
        savedFilm.setGenre("Genre 3");
        savedFilm.setDirector("Director 3");
        savedFilm.setAverageRating(4.0f);

        when(filmRepository.save(film)).thenReturn(savedFilm);

        // when
        Film result = filmService.save(film);

        // then
        assertEquals(savedFilm, result);

        verify(filmRepository, times(1)).save(film);
    }

    @Test
    void testExistsById() {
        // given
        when(filmRepository.existsById(1L)).thenReturn(true);

        // when
        boolean result = filmService.existsById(1L);

        // then
        assertTrue(result);

        verify(filmRepository, times(1)).existsById(1L);
    }


}
