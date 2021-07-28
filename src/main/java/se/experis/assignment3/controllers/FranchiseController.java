package se.experis.assignment3.controllers;

import se.experis.assignment3.models.Character;
import se.experis.assignment3.models.Franchise;
import se.experis.assignment3.models.Movie;
import se.experis.assignment3.repositories.FranchiseRepository;
import se.experis.assignment3.repositories.MovieRepository;
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
    private MovieRepository movieRepository;

    @Autowired
    private FranchiseRepository franchiseRepository;

    /**
     * Adds a Franchise to the DB.
     *
     * @param franchise Franchise to be added.
     * @return the created Franchise.
     */
    @PostMapping
    public ResponseEntity<Franchise> addFranchise(@RequestBody Franchise franchise) {
        Franchise returnFranchise = franchiseRepository.save(franchise);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnFranchise, status);
    }

    /**
     * Returns all the Franchises in the DB.
     *
     * @return a list of all the Franchises.
     */
    @GetMapping()
    public ResponseEntity<List<Franchise>> getAllFranchises() {
        List<Franchise> franchises = franchiseRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(franchises, status);
    }

    /**
     * Fetches a Franchise from the DB matching a given id.
     *
     * @param id used to identify the Franchise.
     * @return the Franchise matching the given id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Franchise> getFranchise(@PathVariable int id) {
        Franchise returnFranchise = new Franchise();
        HttpStatus status;

        if (!franchiseRepository.existsById(id)) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(returnFranchise, status);
        }

        status = HttpStatus.OK;
        returnFranchise = franchiseRepository.findById(id).get();
        return new ResponseEntity<>(returnFranchise, status);
    }

    /**
     * Updates a Franchise in the DB.
     *
     * @param id used to identify the Franchise to be updated.
     * @param franchise the new information that the Franchise will be updated with.
     * @return the updated Franchise.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Franchise> updateFranchise(@PathVariable int id, @RequestBody Franchise franchise) {
        Franchise returnFranchise = new Franchise();
        HttpStatus status;

        if (id != franchise.getFranchiseId()) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnFranchise, status);
        }

        returnFranchise = franchiseRepository.save(franchise);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnFranchise, status);
    }

    /**
     * Deletes a Franchise from the DB.
     *
     * @param id used to identify the Franchise to be deleted.
     * @return response code 200 if the Franchise exists, otherwise 404.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Franchise> deleteFranchise(@PathVariable int id) {
        HttpStatus status = null;

        if (!franchiseRepository.existsById(id)) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }

        status = HttpStatus.OK;

        //Set the franchise's potential relations to null, in order to allow deletion
        Franchise franchise = franchiseRepository.getById(id);
        for (Movie movie : franchise.getMovies()) {
            movie.setFranchise(null);
        }

        franchiseRepository.deleteById(id);
        return new ResponseEntity<>(status);
    }

    /**
     * Update the Movies belonging to a Franchise.
     *
     * @param id the parameter used to identify the Franchise.
     * @param movieIds the id:s of the Movies to be added to the Franchise.
     * @return the updated Franchise.
     */
    @PutMapping("movies/{id}")
    public ResponseEntity<Franchise> updateMovies(@PathVariable int id, @RequestBody List<Integer> movieIds) {
        HttpStatus status = null;
        Franchise franchise = new Franchise();

        if (!franchiseRepository.existsById(id)) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(franchise, status);
        }

        status = HttpStatus.OK;
        franchise = franchiseRepository.findById(id).get();

        //Loop through the movieIds, retrieve the Movies with those id:s and add them to a list
        ArrayList<Movie> movies = new ArrayList<>();
        for (int movieId : movieIds) {
            //Check so a movie exists with that id
            if(movieRepository.existsById(movieId)) {
                Movie movie = movieRepository.getById(movieId);
                movies.add(movie);
                movie.setFranchise(franchise);
                movieRepository.save(movie);
            }
        }

        //Add the Movies to the Franchise
        franchise.setMovies(movies);
        franchiseRepository.save(franchise);
        return new ResponseEntity<>(franchise, status);
    }

    /**
     * Get all the Movies connected to a Franchise.
     *
     * @param id the parameter used to identify the Franchise.
     * @return a list of all the Movies related to the Franchise.
     */
    @GetMapping("/movies/{id}")
    public ResponseEntity<List<Movie>> getMovies(@PathVariable int id) {
        List<Movie> returnList = new ArrayList<>();
        HttpStatus status;

        if (!franchiseRepository.existsById(id)) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(returnList, status);
        }

        status = HttpStatus.OK;
        returnList = franchiseRepository.findById(id).get().getMovies();
        return new ResponseEntity<>(returnList, status);
    }

    /**
     * Get all the Characters connected to the Movies in a Franchise.
     *
     * @param id the parameter used to identify the Franchise.
     * @return a list of all the Characters related to the Franchise.
     */
    @GetMapping("/characters/{id}")
    public ResponseEntity<List<Character>> getCharacters(@PathVariable int id) {
        List<Character> returnList = new ArrayList<>();
        HttpStatus status;

        if (!franchiseRepository.existsById(id)) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(returnList, status);
        }

        status = HttpStatus.OK;
        List<Movie> movies = franchiseRepository.findById(id).get().getMovies();

        for (Movie movie : movies) {
            returnList.addAll(movie.getCharacters());
        }
        return new ResponseEntity<>(returnList, status);
    }
}
