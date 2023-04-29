package database.project.library.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import database.project.library.model.Author;
import database.project.library.repositories.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;
   
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Override
    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        authorRepository.findAll().iterator().forEachRemaining(authors::add);
        
        return authors;
    }

}
