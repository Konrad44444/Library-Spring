package database.project.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import database.project.library.commands.UserCommand;


@Controller
public class IndexController {
    @GetMapping({"", "/", "/index", "/index.html"})
    public String getIndexPage(Model model) {
        model.addAttribute("user", new UserCommand());

        return "index.html";
    }
}
