package com.example.demo.models;

import com.example.demo.models.Gender;

import javax.persistence.*;
import java.net.URL;
import java.util.Set;

@Entity
@Table(name="Character")
public class Character {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int characterId;


    @Column(name="Full_Name")
    private String fullName;
    @Column(name="Alias")
    private String alias;


    @Enumerated(EnumType.STRING)
    @Column(name="Gender")
    private Gender gender;


    @Column(name="Picture")
    private URL picture;

    @ManyToMany(mappedBy="characters")
    public Set<Movie> movies;
}
