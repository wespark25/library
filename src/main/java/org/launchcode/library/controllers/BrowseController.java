package org.launchcode.library.controllers;


import org.launchcode.library.models.*;
import org.launchcode.library.models.data.*;
import org.launchcode.library.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("")
public class BrowseController {

    @Autowired
    AuthorDao authorDao;


    @Autowired
    BookDao bookDao;

    @Autowired
    CartDao cartDao;

    @Autowired
    BackpackDao backpackDao;

    @Autowired
    UserDao userDao;

    @Autowired
    User2Dao user2Dao;

    int backPackid = 7;

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("title", "Behold My Library and Weep!");

        return "browse/index";
    }

    @RequestMapping(value = "browse", method = RequestMethod.GET)
    public String displayBrowseForm(Model model) {
        model.addAttribute("title", "Browse");
//        TODO: Rename this file
        return "browse/browse";
    }

//    TODO FINISH THIS
    @RequestMapping(value = "browse/list/{choice}", method = RequestMethod.GET)
    public String displayFindByForm(Model model) {
            model.addAttribute("title", "Browse: Genres");
            model.addAttribute("genres", Genre.values());
            return "browse/browse-by-genre";
    }

    //    TODO: This is still returning duplicates
    @RequestMapping(value = "genre", method = RequestMethod.GET)
    public String displayGenreList(Model model) {
        model.addAttribute("title", "Genre");
        model.addAttribute("bookList", bookDao.findByGenres(Genre.FANTASY));
        return "browse/list";
    }

    @RequestMapping(value = "browse/book/{bookId}", method = RequestMethod.GET)
    public String displayViewBookForm(Model model, @PathVariable int bookId,
                                      @RequestParam(required = false) String decision,
                                        Principal principal) {

        Book book = bookDao.findById(bookId).get();
        model.addAttribute("title", book.getTitle());

        User2 user2 = user2Dao.findByUsername(principal.getName());
        Cart cart = user2.getCart();

//        Todo: Figure out how to block button from submitting without selection
        if (decision != null) {
            if (decision.equals("add")) {
//                TODO: CHANGE AFTER CLASS IMPLEMENTATION
                cart.addBook(book);
                book.setInCart(true);
                cartDao.save(cart);
            } else if (decision.equals("remove")) {
                book.setInCart(false);
                cart.removeBook(book);
                cartDao.save(cart);
            }
        }
        model.addAttribute("book", book);

        return "book/view";
    }

    @RequestMapping(value = "browse/author/{authorId}")
    public String displayViewAuthorForm(Model model, @PathVariable int authorId) {
        Author author = authorDao.findById(authorId).get();
        model.addAttribute("title", author.getName());
        model.addAttribute("author", author);
        model.addAttribute("bookList", author.getBooks());
        return "author/view";
    }

    @RequestMapping(value = "checkout", method = RequestMethod.GET)
    public String displayCheckoutForm(Model model) {
        model.addAttribute("title", "Checkout");
        model.addAttribute("cart", cartDao.findById(6).get().getBooks());
        return "browse/checkout";
    }

    @RequestMapping(value = "checkout", method = RequestMethod.POST)
    public String processCheckoutForm(Model model) {

        LocalTime time = LocalTime.now().plusMinutes(4);
//        TODO: Change this to find the user's cart after users are implemented
        Cart cart = cartDao.findById(6).get();

//        TODO: CHANGE AFTER USER IMPLEMENTATION
        if (backpackDao.count() == 0){
            Backpack backpack = new Backpack();
            backPackid = backpack.getId();
            backpackDao.save(backpack);
        }
        Backpack backpack = backpackDao.findById(backPackid).get();

        backpack.addBooks(cart.getBooks());
        backpackDao.save(backpack);

        for (Book book : cart.getBooks()) {
            book.setCheckedOut(true);
            book.setInCart(false);
            book.setTimeCheckedOut(time);
            bookDao.save(book);
        }
        cart.removeBooks(cart.getBooks());
        cartDao.save(cart);
        return "browse/checkout";
    }

    @RequestMapping(value = "return", method = RequestMethod.GET)
    public String displayReturnForm(Model model) {
        model.addAttribute("title", "Return Books");
        Backpack backpack = backpackDao.findById(backPackid).get();
//        TODO: CHANGE AFTER USER IMPLEMENTATION
        List<Book> books = backpackDao.findById(backPackid).get().getBooks();
        model.addAttribute("books",books);

        return "browse/return";
    }

    @RequestMapping(value = "return", method = RequestMethod.POST)
    public String processReturnForm(Model model, @RequestParam int[] bookIds) {
        model.addAttribute("title", "Return Books");
        Backpack backpack = backpackDao.findById(backPackid).get();
        for (int id : bookIds) {
            Book book = bookDao.findById(id).get();
            backpack.removeBook(book);
            book.setCheckedOut(false);
            bookDao.save(book);
        }
        backpackDao.save(backpack);

        model.addAttribute("books", backpack.getBooks());
        return "browse/return";
        }


}