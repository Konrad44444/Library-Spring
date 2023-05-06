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


    @Override
    public void saveEditedUserCommand(UserCommand userCommand) {

        Optional<User> userOptional = loginService.getCurrentUser();

        if(userOptional.isPresent()) {
            
            User user = userOptional.get();

            // save changes to user
            user.setLogin(userCommand.getLogin());
            user.setPassword(userCommand.getPassword());
            user.setFirstName(userCommand.getFirstName());
            user.setLastName(userCommand.getLastName());

            userRepository.save(user);

        } else 
            throw new RuntimeException(Util.NO_ACTIVE_USER);

        
    }

}
