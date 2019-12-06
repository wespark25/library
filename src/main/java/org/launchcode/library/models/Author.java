package org.launchcode.library.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Author {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    @OneToMany
    @JoinColumn(name = "author_id")
    private List<Book> books = new ArrayList<>();
}
