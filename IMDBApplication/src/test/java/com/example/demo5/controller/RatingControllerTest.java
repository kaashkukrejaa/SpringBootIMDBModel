package com.example.demo5.controller;

package com.example.demo5.controller;

import com.example.demo5.dto.RatingDTO;
import com.example.demo5.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RatingControllerTest {

    @InjectMocks
    private RatingController ratingController;

    @Mock
    private RatingService ratingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void rateFilm() {
        // given
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setUserId(1L);
        ratingDTO.setFilmId(1L);
        ratingDTO.setRating(4.0f);

        // when
        ratingController.rateFilm(ratingDTO);

        // then
        verify(ratingService, times(1)).rateFilm(1L, 1L, 4.0f);
    }

    @Test
    void rateFilm_invalidRatingValue() {
        // given
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setUserId(1L);
        ratingDTO.setFilmId(1L);
        ratingDTO.setRating(6.0f);

        // when
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> ratingController.rateFilm(ratingDTO));

        // then
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Rating value must be between 1 and 5", exception.getReason());

        verify(ratingService, never()).rateFilm(anyLong(), anyLong(), anyFloat());
    }
}