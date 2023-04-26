package database.project.library.services;

import database.project.library.commands.UserCommand;

public interface RegisterService {
    Boolean registerNewUser(UserCommand userCommand);
}
