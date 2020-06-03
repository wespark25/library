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

            Author deLillo = new Author("Don DeLillo", "American novelist whose postmodernist works portray " +
                    "the anomie of an America cosseted by material excess and stupefied by empty mass culture and politics.");
            Book whiteNoise = new Book("White Noise", deLillo, "When an industrial accident unleashes an" +
                    " \"airborne toxic event\", a lethal black chemical cloud floats over their lives. The menacing cloud" +
                    " is a more urgent and visible version of the \"white noise\" engulfing the Gladneys - radio transmissions," +
                    " sirens, microwaves, ultrasonic appliances, and TV murmurings - pulsing with life yet suggesting" +
                    " something ominous.", genres);
            Book underWorld = new Book("Underworld", deLillo, "While Eisenstein documented the forces " +
                    "of totalitarianism and Stalinism upon the faces of the Russian peoples, DeLillo offers a stunning," +
                    " at times overwhelming, document of the twin forces of the Cold War and American culture, " +
                    "compelling that \"swerve from evenness\" in which he finds events and people both wondrous and" +
                    " horrifying.", genres);
            authorDao.save(deLillo);
            bookDao.save(whiteNoise);
            bookDao.save(underWorld);

            Author wallace = new Author("David Foster Wallace", "American novelist, short-story writer, and " +
                    "essayist whose dense works provide a dark, often satirical analysis of American culture.");
            Book infiniteJest = new Book("Infinite Jest", wallace, "A gargantuan, mind-altering comedy " +
                    "about the pursuit of happiness in America. Set in an addicts' halfway house and a tennis academy, and" +
                    " featuring the most endearingly screwed-up family to come along in recent fiction, Infinite Jest " +
                    "explores essential questions about what entertainment is and why it has come to so dominate our" +
                    " lives; about how our desire for entertainment affects our need to connect with other people; and " +
                    "about what the pleasures we choose say about who we are.\n\n Equal parts philosophical quest and" +
                    " screwball comedy, Infinite Jest bends every rule of fiction without sacrificing for a moment its" +
                    " own entertainment value. It is an exuberant, uniquely American exploration of the passions that " +
                    "make us human - and one of those rare books that renew the idea of what a novel can do.", genres);
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
