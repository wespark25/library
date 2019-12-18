package org.launchcode.library.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String title;

    @NotNull
    private String description;

//    TODO: implement this into add form
//    @NotNull
//    private Date datePublished;

    @ManyToOne
    private Author author;

    @Column
    @ElementCollection
    private List<Genre> genres;

    public Book() {
    }

    public Book(@NotNull String name, @NotNull String description) {
        this.title = name;
        this.description = description;
//        this.datePublished = datePublished;
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

//    public Date getDatePublished() {
//        return datePublished;
//    }
//
//    public void setDatePublished(Date datePublished) {
//        this.datePublished = datePublished;
//    }
}
