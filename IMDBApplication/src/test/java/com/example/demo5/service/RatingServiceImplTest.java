package com.example.demo5.service;

import com.example.demo5.dao.FilmRepository;
import com.example.demo5.dao.RatingRepository;
import com.example.demo5.dao.UserRepository;
import com.example.demo5.entity.Film;
import com.example.demo5.entity.Rating;
import com.example.demo5.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

class RatingServiceImplTest {

    @InjectMocks
    private RatingServiceImpl ratingService;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void rateFilm() {
        // given
        User user = new User();
        user.setId(1L);
        user.setUsername("User 1");
        user.setPassword("password");

        Film film = new Film();
        film.setId(1L);
        film.setName("Film 1");
        film.setGenre("Genre 1");
        film.setDirector("Director 1");
        film.setAverageRating(4.5f);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(filmRepository.findById(1L)).thenReturn(Optional.of(film));
        when(ratingRepository.findByUser_IdAndFilm_Id(1L, 1L)).thenReturn(Optional.empty());

        // when
        ratingService.rateFilm(1L, 1L, 4.0f);

        // then
        verify(userRepository, times(1)).findById(1L);
        verify(filmRepository, times(2)).findById(1L);
        verify(ratingRepository, times(1)).findByUser_IdAndFilm_Id(1L, 1L);
        verify(ratingRepository, times(1)).save(any(Rating.class));
    }
}
