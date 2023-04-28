package database.project.library.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import database.project.library.commands.UserCommand;
import database.project.library.model.User;
import database.project.library.services.LoginService;


@Controller
public class IndexController {
    private static final String MESSAGE = "message";
    private static final String INDEX = "index";
    private static final String USER = "user";
    private static final String NO_LOGIN_USER = "Brak użytkownika o podanym loginie!";
    private static final String PASSWORD_INCORRECT = "Hasło niepoprawne!";
    private static final String REDIRECT_MAIN_VIEW = "redirect:/mainview";


    private final LoginService loginService;

    public IndexController(LoginService loginService) {
        this.loginService = loginService;
    }


    @GetMapping({"", "/", "/index", "/index.html"})
    public String getIndexPage(Model model) {
        Optional<User> logged = loginService.getCurrentUser();

        // if user didn't logged out redirect to main view
        if(logged.isPresent()) return REDIRECT_MAIN_VIEW;

        model.addAttribute(USER, new UserCommand());

        return INDEX;
    }

    @PostMapping("/login")
    public ModelAndView checkLogin(@ModelAttribute UserCommand userCommand) {
        System.out.println("--- Login ---\n" + "Login: " + userCommand.getLogin() + " Password: " + userCommand.getPassword());
        ModelAndView modelAndView = new ModelAndView();
        
        // check if user with inserted login exists
        Boolean isLoginGood = loginService.checkLogin(userCommand);
        
        if(Boolean.FALSE.equals(isLoginGood)) {     //login is incorrect
            modelAndView.addObject(USER, new UserCommand());
            modelAndView.addObject(MESSAGE, NO_LOGIN_USER);
            modelAndView.setViewName(INDEX);

            return modelAndView;
        } else {    // login is correct, then check password
            Boolean isPasswordGood = loginService.checkPassword(userCommand);

            if(Boolean.TRUE.equals(isPasswordGood)) {    //password correct - user logged in
                // set logged user as active
                loginService.setActiveUser(userCommand);

                // redirect to book controller - list of books is the main view
                modelAndView.setViewName(REDIRECT_MAIN_VIEW);

                return modelAndView;
                
            } else {    //password incorrect
                modelAndView.addObject(USER, new UserCommand());
                modelAndView.addObject(MESSAGE, PASSWORD_INCORRECT);
                modelAndView.setViewName(INDEX);
                return modelAndView;
            }

        }

    }

    @GetMapping("/logout")
    public String logout(Model model) {
        System.out.println("Wylogowano");
        loginService.logout();

        model.addAttribute(USER, new UserCommand());

        return INDEX;
    }
}
