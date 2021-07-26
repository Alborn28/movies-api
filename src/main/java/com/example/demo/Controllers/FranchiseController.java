package com.example.demo.Controllers;

import com.example.demo.models.Character;
import com.example.demo.models.Franchise;
import com.example.demo.models.Franchise;
import com.example.demo.repositories.CharactersRepository;
import com.example.demo.repositories.FranchiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/franchises")
public class FranchiseController {



    @Autowired
    private FranchiseRepository franchiseRepository;

    @PostMapping
    public ResponseEntity<Franchise> addFranchise(@RequestBody Franchise franchise){
        Franchise returnFranchise = franchiseRepository.save(franchise);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnFranchise, status);
    }

    @GetMapping()
    public ResponseEntity<List<Franchise>> getAllFranchises(){
        List<Franchise> franchises = franchiseRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(franchises,status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Franchise> getFranchise(@PathVariable int id){
        Franchise returnFranchise = new Franchise();
        HttpStatus status;
        // We first check if the Book exists, this saves some computing time.
        if(franchiseRepository.existsById(id)){
            status = HttpStatus.OK;
            returnFranchise = franchiseRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnFranchise, status);
    }

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

}
