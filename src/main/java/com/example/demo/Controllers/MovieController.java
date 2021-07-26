package com.example.demo.Controllers;

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






}
