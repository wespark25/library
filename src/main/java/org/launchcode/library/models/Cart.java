package org.launchcode.library.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cart {

//    TODO: After User object is created I need to link individual carts with different users.
//    @GeneratedValue
//    @Id
//    public int id;

//    @OneToMany
//    @JoinColumn(name = "book_id")
    static List<Book> books;

//    This will be static while there is only one user
    public static List<Book> getBooks() {
        return books;
    }
    public void addBook(Book book){
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }
}
