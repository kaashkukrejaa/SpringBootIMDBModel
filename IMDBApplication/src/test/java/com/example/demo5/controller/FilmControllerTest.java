package com.example.demo5.controller;

import com.example.demo5.dto.FilmDTO;
import com.example.demo5.entity.Film;
import com.example.demo5.service.FilmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FilmControllerTest {

    @InjectMocks
    private FilmController filmController;

    @Mock
    private FilmService filmService;

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

        when(filmService.findAll()).thenReturn(Arrays.asList(film1, film2));

        // when
        ResponseEntity<List<FilmDTO>> response = filmController.findAll();

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<FilmDTO> filmDTOs = response.getBody();
        assertEquals(2, filmDTOs.size());

        FilmDTO filmDTO1 = filmDTOs.get(0);
        assertEquals(1L, filmDTO1.getId());
        assertEquals("Film 1", filmDTO1.getName());
        assertEquals("Genre 1", filmDTO1.getGenre());
        assertEquals("Director 1", filmDTO1.getDirector());
        assertEquals(4.5f, filmDTO1.getAverageRating());

        FilmDTO filmDTO2 = filmDTOs.get(1);
        assertEquals(2L, filmDTO2.getId());
        assertEquals("Film 2", filmDTO2.getName());
        assertEquals("Genre 2", filmDTO2.getGenre());
        assertEquals("Director 2", filmDTO2.getDirector());
        assertEquals(3.5f, filmDTO2.getAverageRating());

        verify(filmService, times(1)).findAll();
    }

    @Test
    void testFindByName() {
        // given
        Film film1 = new Film();
        film1.setId(1L);
        film1.setName("Film 1");
        film1.setGenre("Genre 1");
        film1.setDirector("Director 1");
        film1.setAverageRating(4.5f);

        when(filmService.findByName("Film 1")).thenReturn(Arrays.asList(film1));

        // when
        ResponseEntity<List<FilmDTO>> response = filmController.findByName("Film 1");

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<FilmDTO> filmDTOs = response.getBody();
        assertEquals(1, filmDTOs.size());

        FilmDTO filmDTO1 = filmDTOs.get(0);
        assertEquals(1L, filmDTO1.getId());
        assertEquals("Film 1", filmDTO1.getName());
        assertEquals("Genre 1", filmDTO1.getGenre());
        assertEquals("Director 1", filmDTO1.getDirector());
        assertEquals(4.5f, filmDTO1.getAverageRating());

        verify(filmService, times(1)).findByName("Film 1");
    }

    @Test
    void testFilmSave() {
        // given
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setName("Film 3");
        filmDTO.setGenre("Genre 3");
        filmDTO.setDirector("Director 3");
        filmDTO.setAverageRating(4.0f);

        Film savedFilm = new Film();
        savedFilm.setId(3L);
        savedFilm.setName("Film 3");
        savedFilm.setGenre("Genre 3");
        savedFilm.setDirector("Director 3");
        savedFilm.setAverageRating(4.0f);

        when(filmService.save(any(Film.class))).thenReturn(savedFilm);

        // when
        ResponseEntity<FilmDTO> response = filmController.save(filmDTO);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        FilmDTO savedFilmDTO = response.getBody();
        assertEquals(3L, savedFilmDTO.getId());
        assertEquals("Film 3", savedFilmDTO.getName());
        assertEquals("Genre 3", savedFilmDTO.getGenre());
        assertEquals("Director 3", savedFilmDTO.getDirector());
        assertEquals(4.0f, savedFilmDTO.getAverageRating());

        verify(filmService, times(1)).save(any(Film.class));
    }

    // other test methods
}
