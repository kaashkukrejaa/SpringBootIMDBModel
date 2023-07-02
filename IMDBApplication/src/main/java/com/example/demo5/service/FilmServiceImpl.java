package com.example.demo5.service;

import com.example.demo5.exception.ExceptionUtils;
import com.example.demo5.dao.FilmRepository;
import com.example.demo5.entity.Film;
import com.example.demo5.logging.MyLogger;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/*
This class contains the business logic for data manipulation of Film Entity
 */
@Service
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;

    private static final Logger logger = MyLogger.getLogger(FilmServiceImpl.class);

    @Autowired
    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public List<Film> findAll() {
        logger.debug("Entering findAll method");
        try {
            return filmRepository.findAll();
        } catch (DataAccessException e) {
            return ExceptionUtils.handleDataAccessException(e, () -> null);
        }
    }

    @Override
    public List<Film> findByName(String name) {
        logger.debug("Entering findByName method with argument name: {}", name);
        List<Film> films;
        try {
            films = filmRepository.findByName(name);
            if(films.isEmpty()) {
                throw new EntityNotFoundException("Film not found with name: " + name);
            }
        } catch (DataAccessException e) {
            return ExceptionUtils.handleDataAccessException(e, () -> null);
        }
        return films;
    }


    @Override
    public Film save(Film film) {
        logger.debug("Entering save method with argument Film: {}", film);
        try {
            return filmRepository.save(film);
        } catch (DataAccessException e) {
            return ExceptionUtils.handleDataAccessException(e, () -> null);
        }
    }

    public boolean existsById(Long id) {
        try{
            return filmRepository.existsById(id);
        } catch (DataAccessException e) {
            return ExceptionUtils.handleDataAccessException(e, () -> null);
        }
    }

}
