package database.project.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import database.project.library.commands.UserCommand;
import database.project.library.services.ProfileService;

@Controller
public class ProfileController {
    private static final String SHOW_PROFILE_MAP = "/profile";
    private static final String SHOW_PROFILE_PATH_LIBRARIAN = "librarian/profile";
    private static final String SHOW_PROFILE_PATH_USER = "user/profile";
    private static final String USER = "user";

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @GetMapping(SHOW_PROFILE_MAP)
    public String showProfile(Model model) {

        UserCommand userCommand = profileService.getCurrentUser();

        model.addAttribute(USER, userCommand);

        if(Boolean.TRUE.equals(userCommand.getIsLibrarian())) 
            return SHOW_PROFILE_PATH_LIBRARIAN;
        else 
            return SHOW_PROFILE_PATH_USER;
        
    }

}
