package com.example.demo.controllers;

import com.example.demo.models.Character;
import com.example.demo.models.Franchise;
import com.example.demo.models.Movie;
import com.example.demo.repositories.FranchiseRepository;
import com.example.demo.repositories.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/franchises")
public class FranchiseController {

    @Autowired
    private MoviesRepository moviesRepository;

    @Autowired
    private FranchiseRepository franchiseRepository;

    /**
     * Adds a single franchise in the DB
     * @param franchise modell to be added
     * @return ResponsEntity with the modell added and a respons code 201.
     */
    @PostMapping
    public ResponseEntity<Franchise> addFranchise(@RequestBody Franchise franchise){
        Franchise returnFranchise = franchiseRepository.save(franchise);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnFranchise, status);
    }

    /**
     *
     * @return a list of all the franchises in the DB.
     */
    @GetMapping()
    public ResponseEntity<List<Franchise>> getAllFranchises(){
        List<Franchise> franchises = franchiseRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(franchises,status);
    }

    /**
     *
     * @param id the parameter used to idenitfy the exact row in the table.
     * @return a single franchises based on the ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Franchise> getFranchise(@PathVariable int id){
        Franchise returnFranchise = new Franchise();
        HttpStatus status;
        if(franchiseRepository.existsById(id)){
            status = HttpStatus.OK;
            returnFranchise = franchiseRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnFranchise, status);
    }

    /**
     *
     * @param id the parameter used to idenitfy the exact row in the table.
     * @param franchise the model used to update the existing row in the table
     * @return ResponsEntity with the modell updated and a response code 204.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Franchise> updateFranchise(@PathVariable int id, @RequestBody Franchise franchise){
        Franchise returnFranchise = new Franchise();
        HttpStatus status;
        /*
         We want to check if the request body matches what we see in the path variable.
         This is to ensure some level of security, making sure someone
         hasn't done some malicious stuff to our body.
        */
        if(id != franchise.getFranchiseId()){
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnFranchise,status);
        }
        returnFranchise = franchiseRepository.save(franchise);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnFranchise, status);
    }

    /**
     *
     * @param id the parameter used to idenitfy the exact row in the table.
     * The method deletes a franchise from the table.
     * Before being able to remove the entity all the FK are set to null in order to break the relation.
     * @return a response code 204.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Franchise> deleteFranchise(@PathVariable int id) {
        // Character returnCharacter = new Character();
        HttpStatus status = null;
        if (franchiseRepository.existsById(id)) {
            status = HttpStatus.OK;
            Franchise franchise = franchiseRepository.getById(id);
            for(Movie movie : franchise.getMovies()) {
                movie.setFranchise(null);
            }

            franchiseRepository.deleteById(id);
        } else {
            status = HttpStatus.NO_CONTENT;
        }

        return new ResponseEntity<>(status);
    }

    /**
     *
     * @param id the parameter used to identify the exact row in the franchise table.
     * @param array the array of movies to be added to this franchise.
     * A franchise object is instantiated and all the movies to be related to the franchise are added to a list.
     * The list is then in turn added to the franchise table.
     * @return ResponseEntity with the model updated and a response code 200.
     */
    @PutMapping("UpdateMovies/{id}")
    public ResponseEntity<Franchise> updateMovies(@PathVariable int id, @RequestBody List<Integer> array){
        HttpStatus status=null;
        Franchise franchise= new Franchise();
        if (franchiseRepository.existsById(id)){
            status = HttpStatus.OK;
            franchise=franchiseRepository.findById(id).get();

            ArrayList<Movie> movies = new ArrayList<>();
            for(int movieId : array) {
                Movie movie = moviesRepository.getById(movieId);
                movies.add(movie);
                movie.setFranchise(franchise);
                moviesRepository.save(movie);
            }

            franchise.setMovies(movies);

            franchiseRepository.save(franchise);
        }
        else {
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(franchise,status);
    }

    /**
     *
     * @param id the parameter used to identify the exact row in the franchise table.
     * @return returns a list of all the movies related to the franchise of the given ID.
     */
    @GetMapping("/movies/{id}")
    public ResponseEntity<List<Movie>> getMovies(@PathVariable int id){
        List<Movie> returnList = new ArrayList<>();
        HttpStatus status;
        // We first check if the Book exists, this saves some computing time.
        if(franchiseRepository.existsById(id)){
            status = HttpStatus.OK;
            returnList = franchiseRepository.findById(id).get().getMovies();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnList, status);
    }

    /**
     *
     * @param id the parameter used to identify the exact row in the franchise table.
     * @return returns a list of all the characters that are related to the movie(s) related to the franchise of the given ID.
     */
    @GetMapping("/characters/{id}")
    public ResponseEntity<List<Character>> getCharacters(@PathVariable int id){
        List<Character> returnList = new ArrayList<>();
        HttpStatus status;
        // We first check if the Book exists, this saves some computing time.
        if(franchiseRepository.existsById(id)){
            status = HttpStatus.OK;
            List<Movie> movies = franchiseRepository.findById(id).get().getMovies();

            for(Movie movie : movies) {
                returnList.addAll(movie.getCharacters());
            }
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnList, status);
    }
}
