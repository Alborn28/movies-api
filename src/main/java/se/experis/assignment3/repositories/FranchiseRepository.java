package se.experis.assignment3.repositories;

import se.experis.assignment3.models.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseRepository extends JpaRepository<Franchise,Integer> {
}
