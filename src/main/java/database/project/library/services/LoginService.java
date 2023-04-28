package database.project.library.services;

import java.util.Optional;

import database.project.library.commands.UserCommand;
import database.project.library.model.User;

public interface LoginService {
    Boolean checkLogin(UserCommand userCommand);
    Boolean checkPassword(UserCommand userCommand);
    void setActiveUser(UserCommand userCommand);
    Boolean checkIfUserIsLibrarian(UserCommand userCommand);
    void logout();
    Optional<User> getCurrentUser(); 
}
