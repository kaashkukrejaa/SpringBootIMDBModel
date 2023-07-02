package com.example.demo5.dto;

public class RatingDTO {

    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long filmId;

    @NotNull
    @Min(1)
    @Max(5)
    private Float rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
