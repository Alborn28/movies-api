package se.experis.assignment3.controllers;

import se.experis.assignment3.models.Character;
import se.experis.assignment3.models.Movie;
import se.experis.assignment3.repositories.CharacterRepository;
import se.experis.assignment3.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/movies")

public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CharacterRepository characterRepository;

    /**
     * Adds a Movie in the DB.
     *
     * @param movie Movie to be added.
     * @return the created Movie.
     */
    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie returnMovie = movieRepository.save(movie);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnMovie, status);
    }

    /**
     * Returns all the Movies in the DB.
     *
     * @return a list of all the Movies.
     */
    @GetMapping()
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(movies, status);
    }


    /**
     * Fetches a Movie from the DB matching a given id.
     *
     * @param id used to identify the Movie.
     * @return the Movie matching the given id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable int id) {
        Movie returnMovie = new Movie();
        HttpStatus status;

        if (!movieRepository.existsById(id)) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(returnMovie, status);
        }

        status = HttpStatus.OK;
        returnMovie = movieRepository.findById(id).get();
        return new ResponseEntity<>(returnMovie, status);
    }

    /**
     * Updates a Movie in the DB.
     *
     * @param id used to identify the Movie to be updated.
     * @param movie the new information that the Movie will be updated with.
     * @return the updated Movie.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie returnMovie = new Movie();
        HttpStatus status;

        if (id != movie.getMovieId()) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnMovie, status);
        }

        returnMovie = movieRepository.save(movie);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnMovie, status);
    }

    /**
     * Deletes a Movie from the DB.
     *
     * @param id used to identify the Movie to be deleted.
     * @return response code 200 if the Movie exists, otherwise 404.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id) {
        HttpStatus status = null;
        if (!movieRepository.existsById(id)) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(status);
        }

        status = HttpStatus.OK;
        movieRepository.deleteById(id);
        return new ResponseEntity<>(status);
    }

    /**
     * Update the Characters belonging to a Movie.
     *
     * @param id the parameter used to identify the Movie.
     * @param characterIds the id:s of the Characters to be added to the Movie.
     * @return the updated Movie.
     */
    @PutMapping("characters/{id}")
    public ResponseEntity<Movie> updateCharacters(@PathVariable int id, @RequestBody List<Integer> characterIds) {
        HttpStatus status = null;
        Movie movie = new Movie();
        if (!movieRepository.existsById(id)) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(movie, status);
        }

        status = HttpStatus.OK;
        movie = movieRepository.findById(id).get();

        //Loop through the characterIds, retrieve the Characters with those id:s and add them to a list
        ArrayList<Character> characters = new ArrayList<>();
        for (int characterId : characterIds) {
            characters.add(characterRepository.getById(characterId));
        }

        //Add the Characters to the Movie
        movie.setCharacters(characters);
        movieRepository.save(movie);
        return new ResponseEntity<>(movie, status);
    }

    /**
     * Get all the Characters connected to a Movie.
     *
     * @param id the parameter used to identify the Movie.
     * @return a list of all the Characters related to the Movie.
     */
    @GetMapping("/characters/{id}")
    public ResponseEntity<List<Character>> getCharacters(@PathVariable int id) {
        List<Character> returnList = new ArrayList<>();
        HttpStatus status;

        if (!movieRepository.existsById(id)) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(returnList, status);
        }

        status = HttpStatus.OK;
        returnList = movieRepository.findById(id).get().getCharacters();
        return new ResponseEntity<>(returnList, status);
    }
}
