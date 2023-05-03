package database.project.library.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import database.project.library.commands.AuthorCommand;
import database.project.library.converters.AuthorToAuthorCommand;
import database.project.library.model.Author;
import database.project.library.repositories.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;
    private final AuthorToAuthorCommand toAuthorCommand;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorToAuthorCommand toAuthorCommand) {
        this.authorRepository = authorRepository;
        this.toAuthorCommand = toAuthorCommand;
    }


    @Override
    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        authorRepository.findAll().iterator().forEachRemaining(authors::add);
        
        return authors;
    }

    @Override
    public AuthorCommand getAuthorCommandById(String id) {
        
        if(Util.isNumeric(id)) {
            Optional<Author> authorOptional = authorRepository.findById(Long.parseLong(id));
            
            if(authorOptional.isPresent())
                return toAuthorCommand.convert(authorOptional.get());
            else 
                throw new RuntimeException(Util.NO_AUTHOR_FOUND);

        } else 
            throw new RuntimeException(Util.INVALID_ID);
    }

    @Override
    public void saveEditedAuthorCommand(AuthorCommand authorCommand) {
        Optional<Author> authorOptional = authorRepository.findById(authorCommand.getId());

        if(authorOptional.isPresent()) {

            Author author = authorOptional.get();
            author.setFirstName(authorCommand.getFirstName());
            author.setLastName(authorCommand.getLastName());

            authorRepository.save(author);

        } else
            throw new RuntimeException(Util.NO_AUTHOR_FOUND);
        
    }


}
