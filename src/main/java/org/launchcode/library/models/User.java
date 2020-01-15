package org.launchcode.library.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @OneToOne
    private Backpack backpack;

    @NotNull
    @OneToOne
    private Cart cart;

    @NotNull
    @Column(unique = true)
    private String username;

    public User() {

    }

    public Backpack getBackpack() {
        return backpack;
    }

    public void setBackpack(Backpack backpack) {
        this.backpack = backpack;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public int getId() {
        return id;
    }

}
