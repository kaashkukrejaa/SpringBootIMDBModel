package com.example.demo5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*

This IMDB application can perform the following functionalities

    1. Users can search for films by name, or see a list of all films.

    2. A film can be rated by users on a scale of 1-5. The current average user rating of each film will be updated accordingly.

    3. The current average rating is displayed along with each film and thus needs to be be retrievable.
 */

@SpringBootApplication
public class IMDB {

    public static void main(String[] args) {
        SpringApplication.run(IMDB.class, args);
    }

}
