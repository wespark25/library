package org.launchcode.library.controllers;

import org.launchcode.library.models.Author;
import org.launchcode.library.models.Book;
import org.launchcode.library.models.data.AuthorDao;
import org.launchcode.library.models.data.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

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

        return "book/add";

    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddBookForm(Model model, @ModelAttribute @Valid Book book, Errors errors,
                                     @RequestParam int authorId){



        if (errors.hasErrors()) {
            model.addAttribute("title", "Add a Book");
            return "book/add";
        }

        model.addAttribute("title", book.getTitle());
        Author author = authorDao.findById(authorId).get();
        book.setAuthor(author);
        bookDao.save(book);
        model.addAttribute("book", book);
        return "book/view";

    }

}
