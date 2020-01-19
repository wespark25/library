package org.launchcode.library.controllers;

import org.launchcode.library.models.Author;
import org.launchcode.library.models.Book;
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

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("author")
public class AuthorController {

    @Autowired
    AuthorDao authorDao;

    @Autowired
    BookDao bookDao;

    @RequestMapping("")
    public String index(Model model) {

        model.addAttribute("title", "Authors");
        model.addAttribute("authors", authorDao.findAll());
        return "author/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddAuthorForm(Model model) {

        model.addAttribute("title", "Add an author");
        model.addAttribute(new Author());
        return "author/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddAuthorForm(Model model, @ModelAttribute @Valid Author author, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add a Author");
            return "author/add";
        }

        model.addAttribute("title", author.getName());
        model.addAttribute("author", author);
        authorDao.save(author);
        return "author/view";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveAuthorForm(Model model) {

        model.addAttribute("title", "Delete Authors");
        model.addAttribute("authors", authorDao.findAll());
        return "author/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveAuthorForm(Model model, @RequestParam int authorId) {
        model.addAttribute("title", "Delete Author");
        Author author = authorDao.findById(authorId).get();
        List<Book> books = author.getBooks();
        for (Book book : books) {
            bookDao.delete(book);
        }
        authorDao.delete(author);;
        model.addAttribute("authors", authorDao.findAll());
        return "author/remove";
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String displayFindByForm(Model model) {
            model.addAttribute("title", "Browse: Authors");
            model.addAttribute("authors", authorDao.findAll());
            return "author/list";
    }
}
