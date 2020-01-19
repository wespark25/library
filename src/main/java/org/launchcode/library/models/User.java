package org.launchcode.library.models;

import org.launchcode.library.models.data.BackpackDao;
import org.launchcode.library.models.data.CartDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class User {

    public User() {
    }

    @Id
    @GeneratedValue
    private int id;

    @Column(unique=true)
    @NotNull
    private String username;

    @Column
    @NotNull
    private String password;

    @NotNull
    @OneToOne
    private Cart cart;

    @NotNull
    @OneToOne
    private Backpack backpack;

    private int active;

    private String roles = " ";

    private String permissions = " ";

    public User(String username, String password, String roles, String permissions, Backpack backpack, Cart cart) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.permissions = permissions;
        this.active = 1;
        this.backpack = backpack;
        this.cart = cart;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getActive() {
        return active;
    }

    public String getRoles() {
        return roles;
    }

    public String getPermissions() {
        return permissions;
    }

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList() {
        if (this.permissions.length() > 0) {
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public void setBackpack(Backpack backpack) {
        this.backpack = backpack;
    }
}
