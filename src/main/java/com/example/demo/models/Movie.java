package com.example.demo.models;

import javax.persistence.*;
import java.net.URL;
import java.util.Set;

@Entity
@Table(name="Movie")
public class Movie {

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int movieId;

    @Column(name="Title")
    private String title;


    @Column(name="Genre")
    private String genre;


    @Column(name="Release_Year")
    private int releaseYear;


    @Column(name="Director")
    private String director;

    @Column(name="Picture")
    private URL picture;


    @Column(name="Trailer")
    private URL trailer;


    @ManyToOne
    @JoinColumn(name="franchise_id")
    private Franchise franchise;

    @ManyToMany
    @JoinTable(
            name="character_movie",
            joinColumns={@JoinColumn(name="movie_id")},
            inverseJoinColumns={@JoinColumn(name="character_id")}
    )
    public Set<Character> characters;



}
