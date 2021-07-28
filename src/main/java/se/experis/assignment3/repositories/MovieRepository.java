package se.experis.assignment3.repositories;

import se.experis.assignment3.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Integer> {
}
