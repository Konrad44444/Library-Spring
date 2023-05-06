package database.project.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import database.project.library.commands.UserCommand;
import database.project.library.services.ProfileService;

@Controller
public class ProfileController {
    private static final String SHOW_PROFILE_MAP = "/profile";
    private static final String SHOW_PROFILE_PATH_LIBRARIAN = "librarian/profile";
    private static final String SHOW_PROFILE_PATH_USER = "user/profile";
    private static final String USER = "user";
    private static final String EDIT_PROFILE_MAP = "/edit/profile";
    private static final String EDIT_PROFILE_PATH= "editprofile";
    private static final String SAVE_EDITED_MAP = "/editprofile";
    private static final String REDIRECT_SHOW_PROFILE = "redirect:" + SHOW_PROFILE_MAP;

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

    @GetMapping(EDIT_PROFILE_MAP)
    public String editProfile(Model model) {

        UserCommand userCommand = profileService.getCurrentUser();

        model.addAttribute(USER, userCommand);

        return EDIT_PROFILE_PATH;
    }

    @PostMapping(SAVE_EDITED_MAP)
    public String saveEditedUser(@ModelAttribute UserCommand userCommand) {

        profileService.saveEditedUserCommand(userCommand);

        return REDIRECT_SHOW_PROFILE;
    }
}
