package se.experis.assignment3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.assignment3.models.Character;
import se.experis.assignment3.repositories.CharacterRepository;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/characters")
public class CharacterController {

    @Autowired
    private CharacterRepository characterRepository;

    /**
     * Adds a Character to the DB.
     *
     * @param character Character to be added.
     * @return the created Character.
     */
    @PostMapping
    public ResponseEntity<Character> addCharacter(@RequestBody Character character) {
        Character returnCharacter = characterRepository.save(character);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnCharacter, status);
    }

    /**
     * Returns all the Characters in the DB.
     *
     * @return a list of all the Characters.
     */
    @GetMapping()
    public ResponseEntity<List<Character>> getAllCharacters() {
        List<Character> characters = characterRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(characters, status);
    }

    /**
     * Fetches a Character from the DB matching a given id.
     *
     * @param id used to identify the Character.
     * @return the Character matching the given id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacter(@PathVariable int id) {
        Character returnCharacter = new Character();
        HttpStatus status;

        if (!characterRepository.existsById(id)) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(returnCharacter, status);
        }

        status = HttpStatus.OK;
        returnCharacter = characterRepository.findById(id).get();
        return new ResponseEntity<>(returnCharacter, status);
    }

    /**
     * Updates a Character in the DB.
     *
     * @param id used to identify the Character to be updated.
     * @param character the new information that the Character will be updated with.
     * @return the updated Character.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Character> updateCharacter(@PathVariable int id, @RequestBody Character character) {
        Character returnCharacter = new Character();
        HttpStatus status;

        if (id != character.getCharacterId()) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnCharacter, status);
        }

        returnCharacter = characterRepository.save(character);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnCharacter, status);
    }

    /**
     * Deletes a Character from the DB.
     *
     * @param id used to identify the Character to be deleted.
     * @return response code 200 if the Character exists, otherwise 404.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Character> deleteCharacter(@PathVariable int id) {
        HttpStatus status = null;

        if (!characterRepository.existsById(id)) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }

        status = HttpStatus.OK;
        characterRepository.deleteById(id);
        return new ResponseEntity<>(status);
    }
}

