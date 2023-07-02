package com.example.demo5.service;

import com.example.demo5.entity.Film;

import java.util.List;


public interface FilmService {
    List<Film> findAll();

    List<Film> findByName(String name);

    Film save(Film film);

    boolean existsById(Long id);
}
