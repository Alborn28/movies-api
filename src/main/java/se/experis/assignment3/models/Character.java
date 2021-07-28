package se.experis.assignment3.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="Character")
public class Character {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int characterId;


    @Column(name="Full_Name",length = 50, nullable = false)
    private String fullName;


    @Column(name="Alias",length = 20)
    private String alias;


    @Column(name="Gender",length = 10)
    private String gender;


    @Column(name="Picture")
    private String picture;

    /**
     * A character can be related to several movies.
     * Thus a linking is created based on the PK in the characters table and FK in the movie table.
     */
    @ManyToMany
    @JoinTable(
            name="character_movie",
            joinColumns={@JoinColumn(name="character_id")},
            inverseJoinColumns={@JoinColumn(name="movie_id")}
    )
    public List<Movie> movies = new ArrayList<>();

    /**
     * if a character is related to a movie or several movies, the references to movies are returned in a list based on the ID of the movie.
     * @return a list of references to the connected movies.
     */
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
