package com.example.demo.Controllers;

import com.example.demo.models.Character;
import com.example.demo.models.Movie;
import com.example.demo.models.Movie;
import com.example.demo.repositories.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/movies")

public class MovieController {

    @Autowired
    private MoviesRepository moviesRepository;

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
        Movie returnMovie = moviesRepository.save(movie);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnMovie, status);
    }

    @GetMapping()
    public ResponseEntity<List<Movie>> getAllMovies(){
        List<Movie> movies = moviesRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(movies,status);
    }

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
    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id) {
        HttpStatus status = null;
        if (moviesRepository.existsById(id)) {
            status = HttpStatus.OK;
            moviesRepository.deleteById(id);
        } else {
            status = HttpStatus.NO_CONTENT;
        }

        return new ResponseEntity<>(status);
    }
}
