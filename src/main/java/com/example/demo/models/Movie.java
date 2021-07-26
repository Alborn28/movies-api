package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private String picture;


    @Column(name="Trailer")
    private String trailer;


    @ManyToOne
    @JoinColumn(name="franchise_id")
    private Franchise franchise;

    @JsonGetter("franchise")
    public String franchise() {
        if(franchise != null){
            return "/api/v1/authors/" + franchise.getFranchiseId();
        }else{
            return null;
        }
    }


    @ManyToMany
    @JoinTable(
            name="character_movie",
            joinColumns={@JoinColumn(name="movie_id")},
            inverseJoinColumns={@JoinColumn(name="character_id")}
    )
    public List<Character> characters = new ArrayList<>();

    @JsonGetter("characters")
    public List<String> characters() {
        return characters.stream()
                .map(character -> {
                    return "/api/v1/characters/" + character.getCharacterId();
                }).collect(Collectors.toList());
    }



    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public Franchise getFranchise() {
        return franchise;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }
}
