package org.launchcode.library.controllers;

import org.launchcode.library.models.Author;
import org.launchcode.library.models.data.AuthorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("author")
public class AuthorController {

    @Autowired
    AuthorDao authorDao;

    @RequestMapping("")
    public String index(Model model){

        model.addAttribute("title", "Authors");
        model.addAttribute("authors", authorDao.findAll());
        return "author/index";

    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddAuthorForm(Model model){

        model.addAttribute("title", "Add an author");
        model.addAttribute(new Author());
        return "author/add";

    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddAuthorForm(Model model, @ModelAttribute @Valid Author author, Errors errors){

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add a Book");
            return "author/add";
        }

        model.addAttribute("title", author.getName());
        return "author/view";

    }
}
