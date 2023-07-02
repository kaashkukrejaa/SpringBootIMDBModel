# Spring Boot IMDB Application

This is a Spring Boot application that provides an API for managing films and ratings. It contains the following :

1. Domain model for data types Film and User created along with the corresponding database layout.

2. Users can search for films by name, or see a list of all films.

3. A film can be rated by users on a scale of 1-5. The current average user rating of each film will be updated accordingly.

4. The current average rating is displayed along with each film and thus needs to be retrievable.

## Installation

To install this application, follow these steps:

1. Clone this repository to your local computer.
2. Install the required dependencies by running `./mvnw install`.
3. Configure the application by updating the `application.properties` file with your database settings.

## Usage

To start the application, run `./mvnw spring-boot:run`. The API will be available at `http://localhost:8080`.

You can use the following endpoints to manage films and ratings:

- `GET /films`: Retrieve a list of all films.
- `GET /films/search?name={name}`: Search for films by name.
- `POST /films`: Create a new film.
- `POST /ratings`: Rate a film.

## Technologies Used

This application is built using the following technologies:

- Spring Boot
- Spring Data JPA
- SQLite Database



