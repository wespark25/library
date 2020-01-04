package org.launchcode.library.controllers;


import org.hibernate.transform.CacheableResultTransformer;
import org.launchcode.library.models.Author;
import org.launchcode.library.models.Book;
import org.launchcode.library.models.Cart;
import org.launchcode.library.models.Genre;
import org.launchcode.library.models.data.AuthorDao;
import org.launchcode.library.models.data.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class BrowseController {

    @Autowired
    AuthorDao authorDao;

    @Autowired
    BookDao bookDao;

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("title", "Behold My Library and Weep!");
        return "checkout/index";
    }

    @RequestMapping(value = "browse", method = RequestMethod.GET)
    public String displayBrowseForm(Model model) {
        model.addAttribute("title", "Browse");
//        model.addAttribute("authors", authorDao.findAll());
        return "checkout/browse";
    }

    @RequestMapping(value = "browse/list/{choice}", method = RequestMethod.GET)
    public String displayFindByForm(Model model, @PathVariable String choice) {
        Iterable<Book> bookList;
        if (choice.equals("all")) {
            model.addAttribute("title", "Browse: All");
            bookList = bookDao.findAll();
            model.addAttribute("bookList", bookList);
        }
        if (choice.equals("author")) {
//            bookList = bookDao.findByAuthor();
            model.addAttribute("title", "Browse: Authors");
            model.addAttribute("authors", authorDao.findAll());
            return "checkout/browse-by-author";
        }
        if (choice.equals("genre")) {
            model.addAttribute("title", "Browse: Genres");
            model.addAttribute("genres", Genre.values());
            return "checkout/genre-by-genre";
        }

        return "checkout/list";
    }

    //    TODO: This is still returning duplicates
    @RequestMapping(value = "genre", method = RequestMethod.GET)
    public String displayGenreList(Model model) {
        model.addAttribute("title", "Genre");
        model.addAttribute("bookList", bookDao.findByGenres(Genre.FANTASY));
        return "checkout/list";
    }

    @RequestMapping(value = "browse/book/{bookId}", method = RequestMethod.GET)
    public String displayViewBookForm(Model model, @PathVariable int bookId,
                                      @RequestParam(required = false) boolean decision) {
        Book book = bookDao.findById(bookId).get();
        model.addAttribute("title", book.getTitle());
        if (decision) {
            book.setInCart(true);
        }
        else if (!decision) {
            book.setInCart(false);
        }
        model.addAttribute("book", book);

        return "book/view";
    }


//    @RequestMapping(value = "browse/book/{bookId}", method = RequestMethod.POST)
//    public String processViewAuthorForm(Model model){
////        TODO
//    }

    @RequestMapping(value = "browse/author/{authorId}")
    public String displayViewAuthorForm(Model model, @PathVariable int authorId) {
        Author author = authorDao.findById(authorId).get();
        model.addAttribute("title", author.getName());
        model.addAttribute("author", author);
        model.addAttribute("bookList", author.getBooks());
        return "author/view";
    }

}