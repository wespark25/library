package org.launchcode.library;

import org.launchcode.library.models.*;
import org.launchcode.library.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {

    @Autowired
    private UserDao userDao;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private BookDao bookDao;
    @Autowired
    AuthorDao authorDao;
    @Autowired
    private BackpackDao backpackDao;
    @Autowired
    private CartDao cartDao;

    public DbInit(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if(bookDao.count() == 0 && authorDao.count() == 0){
            List<Genre> genres = new ArrayList<>();
            genres.add(Genre.FICTION);

            Author deLillo = new Author("Don DeLillo", "Pretty dang good author");
            Book whiteNoise = new Book("White Noise", deLillo, "Don't get me started on that dang barn", genres);
            Book underWorld = new Book("Underworld", deLillo, "This book is a home run", genres);
            authorDao.save(deLillo);
            bookDao.save(whiteNoise);
            bookDao.save(underWorld);

            Author wallace = new Author("David Foster Wallace", "Neurotic");
            Book infiniteJest = new Book("Infinite Jest", wallace, "About as good as it is mentally taxing", genres);
            authorDao.save(wallace);
            bookDao.save(infiniteJest);
        }
        if(userDao.count() != 2) {
            this.userDao.deleteAll();
            this.backpackDao.deleteAll();
            this.cartDao.deleteAll();
            for (Book book : bookDao.findAll()) {
                book.setInCart(false);
                book.setCheckedOut(false);
                bookDao.save(book);
            }

            Backpack backpack = new Backpack();
            backpackDao.save(backpack);
            Cart cart = new Cart();
            cartDao.save(cart);

            Backpack backpack2 = new Backpack();
            backpackDao.save(backpack2);
            Cart cart2 = new Cart();
            cartDao.save(cart2);

            User user = new User("user", passwordEncoder.encode("pass"), "USER", "", backpack, cart);
            User admin = new User("admin", passwordEncoder.encode("pass"), "ADMIN", "", backpack2, cart2);

            List<User> users = Arrays.asList(user, admin);

            this.userDao.saveAll(users);
        }
    }

}
