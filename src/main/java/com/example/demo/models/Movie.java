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

    @Column(name="Title",length = 50)
    private String title;


    @Column(name="Genre",length = 50)
    private String genre;


    @Column(name="Release_Year")
    private int releaseYear;

    @Column(name="Director",length = 50)
    private String director;

    @Column(name="Picture")
    private String picture;


    @Column(name="Trailer")
    private String trailer;

    /**
     * A movie can be related to a franchise
     * Thus a link is created based on the PK in the franchise table and FK in the movie table
     */
    @ManyToOne
    @JoinColumn(name="franchise_id")
    private Franchise franchise;

    /**
     * A movie is related to a franchise, the reference to the franchise is returned as a object based on the ID of the franchised.
     * @return a URL of the reference to the connected franchise
     */
    @JsonGetter("franchise")
    public String franchise() {
        if(franchise != null){
            return "/api/v1/franchises/" + franchise.getFranchiseId();
        }else{
            return null;
        }
    }
    /**
     * A movie can be related to several characters.
     * Thus a link is created based on the PK in the movie table and FK in the character table.
     */
    @ManyToMany
    @JoinTable(
            name="character_movie",
            joinColumns={@JoinColumn(name="movie_id")},
            inverseJoinColumns={@JoinColumn(name="character_id")}
    )
    public List<Character> characters = new ArrayList<>();

    /**
     * if a movie is related to a character or several characters, the references to characters are returned in a list based on the ID of the characters.
     * @return a list of references to the connected characters.
     */
    @JsonGetter("characters")
    public List<String> characters() {
        if (characters == null) {
            return null;
        }
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
