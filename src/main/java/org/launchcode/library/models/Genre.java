package org.launchcode.library.models;

public enum Genre {

    FICTION("Fiction"),
    NONFICTION("Non-fiction"),
    FANTASY("Fantasy"),
    ROMANCE("Romance"),
    CONTEMPORARY("Contemporary"),
    DYSTOPIAN("Dystopian"),
    MYSTERY("Mystery"),
    HORROR("Horror"),
    THRILLER("Thriller");


    private final String name;

    Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
