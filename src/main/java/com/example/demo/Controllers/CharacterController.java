package com.example.demo.Controllers;

import com.example.demo.models.Character;
import com.example.demo.repositories.CharactersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/characters")
public class CharacterController {

    @Autowired
    private CharactersRepository charactersRepository;

    @PostMapping
    public ResponseEntity<Character> addCharacter(@RequestBody Character character) {
        Character returnCharacter = charactersRepository.save(character);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnCharacter, status);
    }

    @GetMapping()
    public ResponseEntity<List<Character>> getAllCharacters() {
        List<Character> characters = charactersRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(characters, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacter(@PathVariable int id) {
        Character returnCharacter = new Character();
        HttpStatus status;
        // We first check if the Book exists, this saves some computing time.
        if (charactersRepository.existsById(id)) {
            status = HttpStatus.OK;
            returnCharacter = charactersRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnCharacter, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Character> updateCharacter(@PathVariable int id, @RequestBody Character character) {
        Character returnCharacter = new Character();
        HttpStatus status;
        /*
         We want to check if the request body matches what we see in the path variable.
         This is to ensure some level of security, making sure someone
         hasn't done some malicious stuff to our body.
        */
        if (id != character.getCharacterId()) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnCharacter, status);
        }
        returnCharacter = charactersRepository.save(character);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnCharacter, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Character> deleteCharacter(@PathVariable int id) {
        // Character returnCharacter = new Character();
        HttpStatus status = null;
        if (charactersRepository.existsById(id)) {
            status = HttpStatus.OK;
            charactersRepository.deleteById(id);
        } else {
            status = HttpStatus.NO_CONTENT;
        }

        return new ResponseEntity<>(status);
    }
}

