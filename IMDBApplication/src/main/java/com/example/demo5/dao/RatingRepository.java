package com.example.demo5.dao;

import com.example.demo5.entity.Film;
import com.example.demo5.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByUser_IdAndFilm_Id(Long userId, Long filmId);
    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.film.id = :filmId")
    Double findAverageRatingByFilm_Id(@Param("filmId") Long filmId);
}
