package org.launchcode.library.controllers;

import org.launchcode.library.models.User;
import org.launchcode.library.models.data.UserDao;
import org.launchcode.library.security.UserPrincipal;
import org.launchcode.library.security.UserPrincipalDetailService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;

//Built for testing purposes
@RestController
@RequestMapping("api/public")
public class PublicRestApiController {
    private UserDao userDao;

    public PublicRestApiController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping("users")
    public Iterable<User> users() {
        return this.userDao.findAll();
    }

    @RequestMapping("userId")
    public String user(Principal principal) {

        return principal.getName();
    }

    @RequestMapping("userAuthorities")
    public Collection<? extends GrantedAuthority> userAuthorities(){

        UserPrincipalDetailService userPrincipalDetailService = new UserPrincipalDetailService(userDao);
        UserPrincipal userPrincipal = (UserPrincipal)userPrincipalDetailService.loadUserByUsername("user");
        return userPrincipal.getAuthorities();
    }
}
