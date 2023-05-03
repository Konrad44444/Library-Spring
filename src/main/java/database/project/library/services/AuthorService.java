package database.project.library.services;

import java.util.List;

import database.project.library.commands.AuthorCommand;
import database.project.library.model.Author;

public interface AuthorService {
    List<Author> getAllAuthors();
    AuthorCommand getAuthorCommandById(String id);
    void saveEditedAuthorCommand(AuthorCommand authorCommand);
}
