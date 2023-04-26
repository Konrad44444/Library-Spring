package database.project.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import database.project.library.commands.UserCommand;
import database.project.library.services.LoginService;


@Controller
public class IndexController {
    private final LoginService loginService;

    public IndexController(LoginService loginService) {
        this.loginService = loginService;
    }


    @GetMapping({"", "/", "/index", "/index.html"})
    public String getIndexPage(Model model) {
        model.addAttribute("user", new UserCommand());

        return "index.html";
    }

    @PostMapping("/login")
    public ModelAndView checkLogin(@ModelAttribute UserCommand userCommand) {
        System.out.println("--- Login ---\n" + "Login: " + userCommand.getLogin() + " Password: " + userCommand.getPassword());
        ModelAndView modelAndView = new ModelAndView();
        
        // check if user with inserted login exists
        Boolean isLoginGood = loginService.checkLogin(userCommand);
        
        if(Boolean.FALSE.equals(isLoginGood)) {
            //login is incorrect
            modelAndView.addObject("user", new UserCommand());
            modelAndView.addObject("message", "Brak użytkownika o podanym loginie!");
            modelAndView.setViewName("index");

            return modelAndView;
        } else {
            // login is correct, then check password
            Boolean isPasswordGood = loginService.checkPassword(userCommand);

            if(Boolean.TRUE.equals(isPasswordGood)) {
                modelAndView.addObject("user", new UserCommand());
                modelAndView.addObject("message", "Zalogowano!");

                //TODO: return antother view and save user as active user inside service function
                modelAndView.setViewName("index");

                return modelAndView;
                
            } else {
                modelAndView.addObject("user", new UserCommand());
                modelAndView.addObject("message", "Hasło niepoprawne!");
                modelAndView.setViewName("index");
                return modelAndView;
            } 
        }

    }
}
