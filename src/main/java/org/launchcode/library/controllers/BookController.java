package org.launchcode.library.controllers;

import org.launchcode.library.models.Book;
import org.launchcode.library.models.data.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("book")
public class BookController {

    @Autowired
    BookDao bookDao;

    @RequestMapping("")
    public String index(Model model){

        model.addAttribute("title", "Welcome to Wesley's Library!");
        return "book/index";

    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddBookForm(Model model){

        model.addAttribute("title", "Add a Book");
        model.addAttribute(new Book());
        return "book/add";

    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddBookForm(Model model, @ModelAttribute @Valid Book book, Errors errors){

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add a Book");
            return "book/add";
        }

        model.addAttribute("title", book.getName());
        return "book/view";

    }

}
