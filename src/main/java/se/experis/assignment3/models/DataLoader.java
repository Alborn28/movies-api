package se.experis.assignment3.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import se.experis.assignment3.repositories.CharacterRepository;
import se.experis.assignment3.repositories.FranchiseRepository;
import se.experis.assignment3.repositories.MovieRepository;

import java.util.ArrayList;

/**
 * A class to seed some initial data into the database.
 */
@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private FranchiseRepository franchiseRepository;

    @Override
    public void run(ApplicationArguments args) {
        //Only fill the database with data if it is empty
        if(characterRepository.findAll().size() != 0 && movieRepository.findAll().size() != 0 && franchiseRepository.findAll().size() != 0) {
            return;
        }

        Character c1 = new Character("Samwise Gamgi", "Sam", "Male", "Picture");
        characterRepository.save(c1);

        Character c2 = new Character( "Frodo Baggins", null, "Male", "Picture");
        characterRepository.save(c2);

        Character c3 = new Character("Peregrin Took", "Pippin", "Male", "Picture");
        characterRepository.save(c3);

        Character c4 = new Character("Meriadoc Brandybuck", "Merry", "Male", "Picture");
        characterRepository.save(c4);

        Character c5 = new Character("Bruce Wayne", "Batman", "Male", "Picture");
        characterRepository.save(c5);

        Franchise f1 = new Franchise("Lord of The Rings", "LOTR");
        franchiseRepository.save(f1);

        Franchise f2 = new Franchise("The Dark Knight", "Batman");
        franchiseRepository.save(f2);

        Movie m1 = new Movie("The Fellowship of The Ring", "Fantasy, Adventure", 2001, "Peter Jackson", "Picture", "Trailer");
        m1.setFranchise(f1);
        ArrayList<Character> characters = new ArrayList<>();
        characters.add(c1);
        characters.add(c2);
        characters.add(c3);
        characters.add(c4);
        m1.setCharacters(characters);
        movieRepository.save(m1);

        Movie m2 = new Movie("The Two Towers", "Fantasy, Adventure", 2002, "Peter Jackson", "Picture", "Trailer");
        m2.setFranchise(f1);
        m2.setCharacters(characters);
        movieRepository.save(m2);

        Movie m3 = new Movie("The Return of The King", "Fantasy, Adventure", 2003, "Peter Jackson", "Picture", "Trailer");
        m3.setFranchise(f1);
        m3.setCharacters(characters);
        movieRepository.save(m3);

        Movie m4 = new Movie("The Dark Knight", "Action, Adventure", 2008, "Christopher Nolan", "Picture", "Trailer");
        m4.setFranchise(f2);
        characters = new ArrayList<Character>();
        characters.add(c5);
        m4.setCharacters(characters);
        movieRepository.save(m4);
    }
}
