package org.launchcode.library.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//This is the class used to store a user's checked out books
@Entity
public class Backpack {

    @GeneratedValue
    @Id
    private int id;

    @OneToOne
    private User user;

    @OneToMany
    @JoinColumn(name = "book_id")
    private List<Book> books = new ArrayList<>();

    public Backpack() {

    }

    public Backpack(List<Book> books) {
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBooks(List<Book> bookList) {
        for (Book book : bookList) {
            books.add(book);
        }
    }

    public void removeBook(Book book) {
            books.remove(book);
    }
    public void empty() {
        this.books.clear();
    }
}
