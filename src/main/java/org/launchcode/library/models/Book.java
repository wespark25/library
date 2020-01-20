package org.launchcode.library.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=1,max=100)
    private String title;

    @NotNull
    @Size(min=1,max=100)
    private String description;

    @ManyToOne
    private Author author;

    @ManyToOne
    Backpack backpack;



    @Column
    @ElementCollection
    private List<Genre> genres;

    private LocalTime timeCheckedOut;

    private boolean inCart;

    private boolean checkedOut;

    public Book() {
    }

    public Book(@NotNull String name, @NotNull Author author, @NotNull String description, List<Genre> genres) {
        this.title = name;
        this.author = author;
        this.description = description;
        this.genres = genres;
        this.inCart = false;
        this.checkedOut = false;
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

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    public LocalTime getTimeCheckedOut() {
        return timeCheckedOut;
    }

    public void setTimeCheckedOut(LocalTime timeCheckedOut) {
        this.timeCheckedOut = timeCheckedOut;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public void setBackpack(Cart cart) {
        this.backpack = backpack;
    }
}
