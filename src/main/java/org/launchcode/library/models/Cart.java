package org.launchcode.library.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    @GeneratedValue
    @Id
    public int id;

    @OneToOne
    private User user;

    @OneToMany
    @JoinColumn(name = "book_id")
    private List<Book> books = new ArrayList<>();

    @NotNull
    private boolean checkedOut = false;

    private LocalTime dueDate;

    public void Cart() {

    }

//    This will be static while there is only one user
    public List<Book> getBooks() {
        return books;
    }
    public void addBook(Book book){
        this.books.add(book);
    }
    public void removeBook(Book book) {
        this.books.remove(book);
    }
    public void empty() {
        this.books.clear();
    }
    public boolean isCheckedOut() {
        return checkedOut;
    }
    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }
    public LocalTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalTime dueDate) {
        this.dueDate = dueDate;
    }
}
