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
    private static final String MESSAGE = "message";
    private static final String INDEX = "index";
    private static final String USER = "user";
    private static final String NO_LOGIN_USER = "Brak użytkownika o podanym loginie!";
    private static final String LOGGED_IN = "Zalogowano!";
    private static final String PASSWORD_INCORRECT = "Hasło niepoprawne!";
    private static final String LIBRARIAN_VIEW_PATH = "/librarian/mainview";
    private static final String USER_VIEW_PATH = "/user/mainview";


    private final LoginService loginService;

    public IndexController(LoginService loginService) {
        this.loginService = loginService;
    }


    @GetMapping({"", "/", "/index", "/index.html"})
    public String getIndexPage(Model model) {
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
                modelAndView.addObject(USER, new UserCommand());
                modelAndView.addObject(MESSAGE, LOGGED_IN);

                // set logged user as active
                loginService.setActiveUser(userCommand);

                // check if user is librarian or not and return proper view
                Boolean userIsLibrarian = loginService.checkIfUserIsLibrarian(userCommand);
                
                if(Boolean.TRUE.equals(userIsLibrarian))
                    modelAndView.setViewName(LIBRARIAN_VIEW_PATH);
                else 
                    modelAndView.setViewName(USER_VIEW_PATH);

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
        loginService.logout();

        model.addAttribute(USER, new UserCommand());

        return INDEX;
    }
}
