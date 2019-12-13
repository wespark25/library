package org.launchcode.library.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

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

    public Book() {
    }

    public Book(@NotNull String name, @NotNull String description) {
        this.title = name;
        this.description = description;
//        this.datePublished = datePublished;
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

//    public Date getDatePublished() {
//        return datePublished;
//    }
//
//    public void setDatePublished(Date datePublished) {
//        this.datePublished = datePublished;
//    }
}
