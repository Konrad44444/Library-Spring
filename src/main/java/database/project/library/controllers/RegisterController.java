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
    private static final String REGISTER = "register";
    private static final String USER = "user";
    private static final String MESSAGE = "message";
    private static final String INDEX = "index";
    private static final String REGISTRATION_GOOD = "Rejestracja przebiegła pomyślnie!";
    private static final String REGISTRATION_BAD_LOGIN_EXISTS = "Użytkownik o podanym loginie istnieje!";

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }    


    @GetMapping({"/register", "/register/"})
    public String showRegisterPage(Model model) {
        model.addAttribute(USER, new UserCommand());

        return REGISTER;
    }

    @PostMapping("/register/new")
    public ModelAndView registerNewUser(@ModelAttribute UserCommand userCommand) {
        userCommand.setIsLibrarian(false);

        Boolean isRegisterGood = registerService.registerNewUser(userCommand);

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject(USER, new UserCommand());
        
        if(Boolean.TRUE.equals(isRegisterGood)) {
            modelAndView.addObject(MESSAGE, REGISTRATION_GOOD);
            modelAndView.setViewName(INDEX);
            return modelAndView;
        } else {
            modelAndView.addObject(MESSAGE, REGISTRATION_BAD_LOGIN_EXISTS);
            modelAndView.setViewName(REGISTER);
            return modelAndView;
        }
        
    }
}
