package com.example.demo.repositories;

import com.example.demo.models.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseRepository extends JpaRepository<Franchise,Integer> {
}
