package com.example.demo.controllers;

import com.example.demo.models.Character;
import com.example.demo.models.Movie;
import com.example.demo.repositories.CharactersRepository;
import com.example.demo.repositories.MoviesRepository;
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
    private MoviesRepository moviesRepository;

    @Autowired
    private CharactersRepository charactersRepository;

    /**
     * Adds a single movie in the DB
     * @param movie model to be added
     * @return ResponseEntity with the model added and a response code 201.
     */
    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
        Movie returnMovie = moviesRepository.save(movie);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnMovie, status);
    }

    /**
     *
     * @return a list of all the movies in the DB.
     */
    @GetMapping()
    public ResponseEntity<List<Movie>> getAllMovies(){
        List<Movie> movies = moviesRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(movies,status);
    }


    /**
     *
     * @param id the parameter used to idenitfy the exact row in the table.
     * @return a single movie based on the ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable int id){
        Movie returnMovie = new Movie();
        HttpStatus status;
        // We first check if the Book exists, this saves some computing time.
        if(moviesRepository.existsById(id)){
            status = HttpStatus.OK;
            returnMovie = moviesRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnMovie, status);
    }
    /**
     *
     * @param id the parameter used to idenitfy the exact row in the table.
     * @param movie the model used to update the existing row in the table
     * @return ResponseEntity with the model updated and a response code 204.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie){
        Movie returnMovie = new Movie();
        HttpStatus status;
        /*
         We want to check if the request body matches what we see in the path variable.
         This is to ensure some level of security, making sure someone
         hasn't done some malicious stuff to our body.
        */
        if(id != movie.getMovieId()){
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnMovie,status);
        }

        returnMovie = moviesRepository.save(movie);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnMovie, status);
    }

    /**
     *
     * @param id the parameter used to idenitfy the exact row in the table.
     * The method deletes a movie from the table.
     * @return a response code 204.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id) {
        HttpStatus status = null;
        if (moviesRepository.existsById(id)) {
            status = HttpStatus.OK;
            moviesRepository.deleteById(id);
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(status);
    }

    /**
     *
     * @param id the parameter used to identify the exact row in the movie table.
     * @param array the array of characters to be added to this movie.
     * A movie object is instantiated and all the characters to be related to the movie are added to a list.
     * The list is then in turn added to the movie table.
     * @return ResponseEntity with the model updated and a response code 200.
     */
    @PutMapping("UpdateCharacters/{id}")
    public ResponseEntity<Movie> updateCharacters(@PathVariable int id, @RequestBody List<Integer> array){
        HttpStatus status=null;
        Movie movie= new Movie();
        if (moviesRepository.existsById(id)){
            status = HttpStatus.OK;
            movie=moviesRepository.findById(id).get();

            ArrayList<Character> characters = new ArrayList<>();
            for(int characterId : array) {
                characters.add(charactersRepository.getById(characterId));
            }

            movie.setCharacters(characters);

            moviesRepository.save(movie);
        }
        else {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(movie,status);
    }

    /**
     *
     * @param id the parameter used to identify the exact row in the movie table.
     * @return returns a list of all the characters related to the movie of the given ID.
     */
    @GetMapping("/characters/{id}")
    public ResponseEntity<List<Character>> getCharacters(@PathVariable int id){
        List<Character> returnList = new ArrayList<>();
        HttpStatus status;
        // We first check if the Book exists, this saves some computing time.
        if(moviesRepository.existsById(id)){
            status = HttpStatus.OK;
            returnList = moviesRepository.findById(id).get().getCharacters();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnList, status);
    }
}
