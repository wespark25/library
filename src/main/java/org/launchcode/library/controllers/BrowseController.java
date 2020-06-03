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

    UserPrincipal userPrincipal;

    @RequestMapping("")
    public String index(Model model) {

        model.addAttribute("title", "Behold My Library!");

        return "browse/index";
    }

    @RequestMapping(value = "browse", method = RequestMethod.GET)
    public String displayBrowseForm(Model model) {

        model.addAttribute("title", "Browse");

        return "browse/browse";
    }

//
    @RequestMapping(value = "browse/list/genre", method = RequestMethod.GET)
    public String displayFindByForm(Model model) {

        model.addAttribute("title", "Browse: Genres");
        model.addAttribute("genres", Genre.values());

        return "browse/browse-by-genre";
    }

//    To Fix
    @RequestMapping(value = "browse/list/genre", method = RequestMethod.POST)
    public String displayGenreList(Model model, @RequestParam Genre genre) {

        model.addAttribute("title", "Genre");
        List<Book> books = bookDao.findByGenresContaining(genre);
//        List<Book> bookList = new ArrayList<>();
//        for (int i = 0; i < books.size(); i = i+2) {
//            bookList.add(books.get(i));
//        }
        model.addAttribute("bookList", books);

        return "book/list";
    }

    @RequestMapping(value = "browse/book/{bookId}", method = RequestMethod.GET)
    public String displayViewBookForm(Model model, @PathVariable int bookId,
                                        Principal principal) {

        Book book = bookDao.findById(bookId).get();
        model.addAttribute("title", book.getTitle());
        model.addAttribute("buttonText", book.isInCart() ? "Remove from cart" : "Add to cart");
        model.addAttribute("book", book);
        model.addAttribute("user", userDao.findByUsername(principal.getName()));

        return "book/view";
    }

    @RequestMapping(value = "browse/book/{bookId}", method = RequestMethod.POST)
    public String processViewBookForm(Model model, @PathVariable int bookId,
                                      @RequestParam String decision,
                                      Principal principal) {

        Book book = bookDao.findById(bookId).get();
        model.addAttribute("title", book.getTitle());
        model.addAttribute("book", book);

        User user = userDao.findByUsername(principal.getName());
        Cart cart = user.getCart();

            if (decision.equals("add")) {
                book.setInCart(true);
                cart.addBook(book);
                cartDao.save(cart);
            }
            else if (decision.equals("remove")) {
                book.setInCart(false);
                cart.removeBook(book);
                cartDao.save(cart);
            }

        model.addAttribute("buttonText", book.isInCart() ? "Remove from cart" : "Add to cart");

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
    public String displayCheckoutForm(Model model, Principal principal) {

        model.addAttribute("title", "Checkout");
        User user = userDao.findByUsername(principal.getName());
        Cart cart = user.getCart();
        model.addAttribute("cart", cart.getBooks());

        return "browse/checkout";
    }

    @RequestMapping(value = "checkout", method = RequestMethod.POST)
    public String processCheckoutForm(Model model, Principal principal) {

        model.addAttribute("title", "Checkout");
        LocalTime time = LocalTime.now().plusMinutes(4);

        User user = userDao.findByUsername(principal.getName());
        Cart cart = user.getCart();
        Backpack backpack = user.getBackpack();

        backpack.addBooks(cart.getBooks());
        backpackDao.save(backpack);

        List<Book> bookList = cart.getBooks();
        for (Book book : bookList) {
            book.setCheckedOut(true);
            book.setInCart(false);
            book.setTimeCheckedOut(time);
            bookDao.save(book);
        }

        cart.empty();
        cartDao.save(cart);

        return "browse/checkout";
    }

    @RequestMapping(value = "return", method = RequestMethod.GET)
    public String displayReturnForm(Model model, Principal principal) {

        model.addAttribute("title", "Return Books");

        User user = userDao.findByUsername(principal.getName());
        Cart cart = user.getCart();
        Backpack backpack = user.getBackpack();

        model.addAttribute("books",backpack.getBooks());

        return "browse/return";
    }

    @RequestMapping(value = "return", method = RequestMethod.POST)
    public String processReturnForm(Model model, @RequestParam(required = false) int[] bookIds, Principal principal) {

        model.addAttribute("title", "Return Books");

        User user = userDao.findByUsername(principal.getName());
        Backpack backpack = user.getBackpack();
        if (bookIds != null) {
            for (int id : bookIds) {
            Book book = bookDao.findById(id).get();
            book.setCheckedOut(false);
            bookDao.save(book);
            backpack.removeBook(book);
        }

        backpackDao.save(backpack);
        }
        model.addAttribute("message", "Select at least one book");
        model.addAttribute("books", backpack.getBooks());

        return "browse/return";
        }
}