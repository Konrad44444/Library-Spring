package database.project.library.services;

import database.project.library.commands.UserCommand;

public interface LoginService {
    Boolean checkLogin(UserCommand userCommand);
    Boolean checkPassword(UserCommand userCommand);
    void setActiveUser(UserCommand userCommand);
    Boolean checkIfUserIsLibrarian(UserCommand userCommand);
    void logout();
}
