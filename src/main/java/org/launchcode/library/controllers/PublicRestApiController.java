package org.launchcode.library.controllers;

import org.launchcode.library.models.User2;
import org.launchcode.library.models.data.User2Dao;
import org.launchcode.library.security.UserPrincipal;
import org.launchcode.library.security.UserPrincipalDetailService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.expression.Lists;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/public")
public class PublicRestApiController {
    private User2Dao user2Dao;

    public PublicRestApiController(User2Dao user2Dao) {
        this.user2Dao = user2Dao;
    }

    @RequestMapping("users")
    public Iterable<User2> users() {
        return this.user2Dao.findAll();
    }

    @RequestMapping("userId")
    public String user(Principal userPrincipal){


        return userPrincipal.getName();
    }
}
