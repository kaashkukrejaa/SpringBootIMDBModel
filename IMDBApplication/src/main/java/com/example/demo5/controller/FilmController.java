package com.example.demo5.controller;

import com.example.demo5.dto.FilmDTO;
import com.example.demo5.entity.Film;
import com.example.demo5.service.FilmService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/*
This class is responsible for handling HTTP requests related to films
 */
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public ResponseEntity<List<FilmDTO>> findAll() {
        List<Film> films = filmService.findAll();
        if(films.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"no records found");
        }
        List<FilmDTO> filmDTOs = films.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(filmDTOs, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<FilmDTO>> findByName(@RequestParam String name) {
        List<Film> films;
        try {
            films = filmService.findByName(name);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
        List<FilmDTO> filmDTOs = films.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(filmDTOs, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<FilmDTO> save(@Valid @RequestBody FilmDTO filmDTO) {
        Film film = convertToEntity(filmDTO);
        if (film.getId() != null && filmService.existsById(film.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A Film already exists with the given id: " + film.getId());
        }
        Film savedFilm = filmService.save(film);
        return new ResponseEntity<>(convertToDTO(savedFilm), HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    private Film convertToEntity(FilmDTO filmDTO) {
        Film film = new Film();
        film.setId(filmDTO.getId());
        film.setName(filmDTO.getName());
        film.setGenre(filmDTO.getGenre());
        film.setDirector(filmDTO.getDirector());
        film.setAverageRating(filmDTO.getAverageRating());
        return film;
    }

    private FilmDTO convertToDTO(Film film) {
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setId(film.getId());
        filmDTO.setName(film.getName());
        filmDTO.setGenre(film.getGenre());
        filmDTO.setDirector(film.getDirector());
        filmDTO.setAverageRating(film.getAverageRating());
        return filmDTO;
    }
}