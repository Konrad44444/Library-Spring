package database.project.library.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import database.project.library.commands.UserCommand;
import database.project.library.converters.UserToUserCommand;
import database.project.library.model.User;
import database.project.library.repositories.UserRepository;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final LoginService loginService;
    private final UserRepository userRepository;
    private final UserToUserCommand toUserCommand;

    public ProfileServiceImpl(LoginService loginService, UserRepository userRepository, UserToUserCommand userToUserCommand) {
        this.loginService = loginService;
        this.userRepository = userRepository;
        toUserCommand = userToUserCommand;
    }


    @Override
    public UserCommand getCurrentUser() {
        
        Optional<User> userOptional = loginService.getCurrentUser();

        if(userOptional.isPresent()) {
            
            return toUserCommand.convert(userOptional.get());

        } else 
            throw new RuntimeException(Util.NO_ACTIVE_USER);

    }
 
}
