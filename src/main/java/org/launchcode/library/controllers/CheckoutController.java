package org.launchcode.library.controllers;


import org.launchcode.library.models.Author;
import org.launchcode.library.models.Book;
import org.launchcode.library.models.data.AuthorDao;
import org.launchcode.library.models.data.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("")
public class CheckoutController {

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
        model.addAttribute("authors", authorDao.findAll());
        return "checkout/browse";
    }

    @RequestMapping(value = "browse", method = RequestMethod.POST)
    public String processBrowseForm(Model model, @RequestParam int authorId) {
        model.addAttribute("title", "Browse");
        model.addAttribute("authors", authorDao.findAll());
        Author author = authorDao.findById(authorId).get();
        List<Book> bookList = bookDao.findByAuthor(author);
        model.addAttribute("bookList", bookList);
        return "checkout/browse";
    }
}
