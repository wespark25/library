package org.launchcode.library.controllers;

import org.launchcode.library.models.Author;
import org.launchcode.library.models.Book;
import org.launchcode.library.models.Genre;
import org.launchcode.library.models.data.AuthorDao;
import org.launchcode.library.models.data.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@Controller
@RequestMapping("book")
public class BookController {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private AuthorDao authorDao;

    @RequestMapping("")
    public String index(Model model){

        model.addAttribute("title", "Welcome to Wesley's Library!");
        return "book/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddBookForm(Model model){

        model.addAttribute("title", "Add a Book");
        model.addAttribute(new Book());
        model.addAttribute("authors",authorDao.findAll());
        model.addAttribute(("genres"), Genre.values());

        return "book/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddBookForm(Model model, @ModelAttribute @Valid Book book, Errors errors,
                                     @RequestParam int authorId,  @RequestParam(required=false) Genre[] genres){

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add a Book");
            model.addAttribute("authors",authorDao.findAll());
            model.addAttribute(("genres"), Genre.values());
            return "book/add";
        }

        for (Genre genre : genres) {
            book.addGenre(genre);
        }

//        TODO: Add the Genre feature

        model.addAttribute("title", book.getTitle());
        Author author = authorDao.findById(authorId).get();
        book.setAuthor(author);
        bookDao.save(book);
        model.addAttribute("book", book);
        return "book/view";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveBookForm(Model model) {

        model.addAttribute("title", "Delete Books");
        model.addAttribute("books", bookDao.findAll());
        return "book/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveBookForm(Model model, @RequestParam int[] bookIds) {
        model.addAttribute("title", "Delete Books");
        for (int id : bookIds) {
            bookDao.delete(bookDao.findById(id).get());
        }
        model.addAttribute("books", bookDao.findAll());
        return "book/remove";
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String displayFindByForm(Model model) {
        model.addAttribute("title", "Browse: All");
        Iterable<Book> bookList = bookDao.findAll();
        model.addAttribute("bookList", bookList);
        return "book/list";
    }
}
