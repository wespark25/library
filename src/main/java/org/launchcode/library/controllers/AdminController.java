package org.launchcode.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("templates/admin")
public class AdminController {

//    TODO:
    @RequestMapping("")
    public String index(Model model){
        model.addAttribute("title", "Welcome Admin");

        return "admin/index";
    }
}
