package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="Franchise")
public class Franchise {

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int franchiseId;

    @Column(name="Name",length = 50)
    private String name;


    @Column(name="Description")
    private String description;

    @OneToMany(mappedBy = "franchise")
    private List<Movie> movies = new ArrayList<>();

    @JsonGetter("movies")
    public List<String> movies() {
        return movies.stream()
                .map(movie -> {
                    return "/api/v1/movies/" + movie.getMovieId();
                }).collect(Collectors.toList());
    }

    public int getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(int franchiseId) {
        this.franchiseId = franchiseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
