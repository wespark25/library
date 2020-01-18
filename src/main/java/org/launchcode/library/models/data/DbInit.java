package org.launchcode.library.models.data;

import org.launchcode.library.models.Backpack;
import org.launchcode.library.models.Cart;
import org.launchcode.library.models.User2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {

    @Autowired
    private User2Dao user2Dao;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private BackpackDao backpackDao;

    @Autowired
    private CartDao cartDao;

    public DbInit(User2Dao user2Dao, PasswordEncoder passwordEncoder) {
        this.user2Dao = user2Dao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        this.user2Dao.deleteAll();
        this.backpackDao.deleteAll();
        this.cartDao.deleteAll();

        Backpack backpack = new Backpack();
        backpackDao.save(backpack);
        Cart cart = new Cart();
        cartDao.save(cart);

        Backpack backpack2 = new Backpack();
        backpackDao.save(backpack2);
        Cart cart2 = new Cart();
        cartDao.save(cart2);

        User2 user = new User2("user",passwordEncoder.encode("pass"),"USER","",backpack,cart);
        User2 admin = new User2("admin",passwordEncoder.encode("pass"),"ADMIN","",backpack2,cart2);

        List<User2> users = Arrays.asList(user, admin);

        this.user2Dao.saveAll(users);
    }

}
