package database.project.library.services;

import database.project.library.commands.UserCommand;

public interface ProfileService {
    UserCommand getCurrentUser();
    void saveEditedUserCommand(UserCommand userCommand);
}
