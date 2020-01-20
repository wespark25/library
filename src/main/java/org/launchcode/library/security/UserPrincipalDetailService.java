package org.launchcode.library.security;

import org.launchcode.library.models.User;
import org.launchcode.library.models.data.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailService implements UserDetailsService {
    private UserDao userDao;

    public UserPrincipalDetailService(UserDao userDao) {
        this.userDao = userDao;
    }

//    TODO (RYAN): if the user logs in with the wrong credentials he is stuck and it won't send him back to the login
//    TODO (RYAN) page and show him the appropriate message, which it should do automatically. Any thoughts on how to fix this?
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException{

            User user = this.userDao.findByUsername(s);
            UserPrincipal userPrincipal = new UserPrincipal(user);

        return userPrincipal;
    }
}
