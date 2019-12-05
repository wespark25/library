package org.launchcode.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("book")
public class BookController {

    @RequestMapping("")
    public String index(Model model){
        return "book/index";
    }
}
