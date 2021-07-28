package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="Character")
public class Character {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int characterId;


    @Column(name="Full_Name",length = 50)
    private String fullName;


    @Column(name="Alias",length = 20)
    private String alias;


    @Column(name="Gender",length = 10)
    private String gender;


    @Column(name="Picture")
    private String picture;

    @ManyToMany
    @JoinTable(
            name="character_movie",
            joinColumns={@JoinColumn(name="character_id")},
            inverseJoinColumns={@JoinColumn(name="movie_id")}
    )
    public List<Movie> movies = new ArrayList<>();

    @JsonGetter("movies")
    public List<String> movies() {
        if(movies != null){
            return movies.stream()
                    .map(movie -> {
                        return "/api/v1/movies/" + movie.getMovieId();
                    }).collect(Collectors.toList());
        }
        return null;
    }


    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
