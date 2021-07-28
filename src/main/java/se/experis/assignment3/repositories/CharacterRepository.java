package se.experis.assignment3.repositories;

import se.experis.assignment3.models.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character,Integer> {
}
