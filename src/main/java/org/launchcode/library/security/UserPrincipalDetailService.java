package org.launchcode.library.security;

import org.launchcode.library.models.User2;
import org.launchcode.library.models.data.User2Dao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailService implements UserDetailsService {
    private User2Dao user2Dao;

    public UserPrincipalDetailService(User2Dao user2Dao) {
        this.user2Dao = user2Dao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException{

            User2 user2 = this.user2Dao.findByUsername(s);
            UserPrincipal userPrincipal = new UserPrincipal(user2);

        return userPrincipal;
    }
}
