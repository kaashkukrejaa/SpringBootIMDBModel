package com.example.demo5.controller;

import com.example.demo5.dto.RatingDTO;
import com.example.demo5.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.stream.Collectors;

/*
This class is responsible for handling HTTP requests related to Ratings
 */
@RestController
@RequestMapping("/ratings")
public class RatingController {
    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public void rateFilm(@Valid @RequestBody RatingDTO ratingDTO) {
        Long userId = ratingDTO.getUserId();
        Long filmId = ratingDTO.getFilmId();
        Float ratingValue = ratingDTO.getRating();

        try {
            ratingService.rateFilm(userId, filmId, ratingValue);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getReason());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}