package database.project.library.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import database.project.library.commands.UserCommand;
import database.project.library.converters.UserCommandToUser;
import database.project.library.model.User;
import database.project.library.repositories.UserRepository;

@Service
public class RegisterServiceImpl implements RegisterService{
    private final UserRepository userRepository;
    private final UserCommandToUser toUser;

    public RegisterServiceImpl(UserRepository userRepository, UserCommandToUser toUser) {
        this.userRepository = userRepository;
        this.toUser = toUser;
    }


    @Override
    public Boolean registerNewUser(UserCommand userCommand) {
        if(userCommand == null) return false;

        User user = toUser.convert(userCommand);

        Optional<User> userFromDB = userRepository.findByLogin(user.getLogin());

        // if user with the same login exists return false
        if(userFromDB.isPresent()) return false;

        // if not save user and return true
        user.setBasket(null);
        userRepository.save(user);

        return true;
    }

    

}
