package com.example.demo.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="Franchise")
public class Franchise {

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int franchiseId;

    @Column(name="Name")
    private String name;


    @Column(name="Description")
    private String description;

    @OneToMany(mappedBy = "franchise")
    private Set<Movie> movies;
}
