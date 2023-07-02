package com.example.demo5.service;

import com.example.demo5.exception.ExceptionUtils;
import com.example.demo5.dao.FilmRepository;
import com.example.demo5.dao.RatingRepository;
import com.example.demo5.dao.UserRepository;
import com.example.demo5.entity.Film;
import com.example.demo5.entity.Rating;
import com.example.demo5.entity.User;
import com.example.demo5.logging.MyLogger;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.function.BiFunction;

/*
This class contains the business logic for data manipulation of Rating Entity
 */
@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    private static final Logger logger = MyLogger.getLogger(RatingServiceImpl.class);

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, FilmRepository filmRepository, UserRepository userRepository) {
        this.ratingRepository = ratingRepository;
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void rateFilm(Long userId, Long filmId, Float ratingValue) {
        logger.debug("Entering rateFilm method with arguments userId = {}, filmId = {}, ratingValue={}", userId, filmId, ratingValue);

        try {
            // check if the user and film exist
            User user = findUserById(userId);
            Film film = findFilmById(filmId);

            // create or update the rating
            Rating rating = createOrUpdateRating(userId, filmId, ratingValue);
            ratingRepository.save(rating);

            updateAverageRating(film);
        } catch (DataAccessException e) {
            ExceptionUtils.handleDataAccessException(e, () -> null);
        }
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    private Film findFilmById(Long filmId) {
        return filmRepository.findById(filmId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Film not found"));
    }

    private Rating createOrUpdateRating(Long userId, Long filmId, Float ratingValue) {
        // check if the user has already rated this film
        Optional<Rating> existingRating = ratingRepository.findByUser_IdAndFilm_Id(userId, filmId);
        Rating rating;
        if (existingRating.isPresent()) {
            // update the existing rating
            rating = existingRating.get();
        } else {
            // create a new rating
            rating = new Rating();
            User user = findUserById(userId);
            Film film = findFilmById(filmId);
            rating.setUser(user);
            rating.setFilm(film);
        }
        rating.setRating(ratingValue);
        return rating;
    }

    private void updateAverageRating(Film film) {
        Double averageRating = ratingRepository.findAverageRatingByFilm_Id(film.getId());
        film = filmRepository.findById(film.getId()).orElseThrow();
        BiFunction<Float, Float, Float> average = (num1, num2) -> (float) ((num1 + num2) / 2.0);
        film.setAverageRating(average.apply(averageRating.floatValue(), film.getAverageRating()));
        filmRepository.save(film);
    }
}
