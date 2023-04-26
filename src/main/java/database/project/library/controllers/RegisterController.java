package database.project.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import database.project.library.commands.UserCommand;
import database.project.library.services.RegisterService;

@Controller
public class RegisterController {
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }    


    @GetMapping({"/register", "/register/"})
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new UserCommand());

        return "register.html";
    }

    @PostMapping("/register/new")
    public ModelAndView registerNewUser(@ModelAttribute UserCommand userCommand) {
        userCommand.setIsLibrarian(false);

        Boolean isRegisterGood = registerService.registerNewUser(userCommand);

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("user", new UserCommand());
        
        if(Boolean.TRUE.equals(isRegisterGood)) {
            modelAndView.addObject("message", "Rejestracja przebiegła pomyślnie!");
            modelAndView.setViewName("index");
            return modelAndView;
        } else {
            modelAndView.addObject("message", "Użytkownik o podanym loginie istnieje");
            modelAndView.setViewName("register");
            return modelAndView;
        }
        
    }
}
