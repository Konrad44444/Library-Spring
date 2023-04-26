package database.project.library.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import database.project.library.commands.UserCommand;
import database.project.library.converters.UserCommandToUser;
import database.project.library.model.Active;
import database.project.library.model.User;
import database.project.library.repositories.ActiveRepository;
import database.project.library.repositories.UserRepository;

@Service
public class LoginServiceImpl implements LoginService{
    private final UserRepository userRepository;
    private final UserCommandToUser toUser;
    private final ActiveRepository activeRepository;

    public LoginServiceImpl(UserRepository userRepository, UserCommandToUser toUser, ActiveRepository activeRepository) {
        this.userRepository = userRepository;
        this.toUser = toUser;
        this.activeRepository = activeRepository;
    }


    @Override
    public Boolean checkLogin(UserCommand userCommand) {
        // empty command - return null
        if(userCommand == null) return null;

        User user = toUser.convert(userCommand);

        //check if user with this login exists
        String userLogin = user.getLogin();
        
        Optional<User> userFromDB = userRepository.findByLogin(userLogin);
        
        // user with this login doesn't exist - return false
        if(!userFromDB.isPresent()) return false;

        return true;
    }

    @Override
    public Boolean checkPassword(UserCommand userCommand) {
        // empty command - return null
        if(userCommand == null) return null;

        User user = toUser.convert(userCommand);

        String userPassword = user.getPassword();

        // it will exists - checked before
        Optional<User> userFromDB = userRepository.findByLogin(user.getLogin());

        String passwordFromDB = userFromDB.get().getPassword();

        return userPassword.equals(passwordFromDB);
    }


    @Override
    public void setActiveUser(UserCommand userCommand) {
        Optional<User> activeUser = userRepository.findByLogin(toUser.convert(userCommand).getLogin());

        Active active = new Active();
        active.setNumber(activeUser.get().getId());

        activeRepository.save(active);
    }

    @Override
    public Boolean checkIfUserIsLibrarian(UserCommand userCommand) {
        Optional<User> activeUser = userRepository.findByLogin(toUser.convert(userCommand).getLogin());

        return activeUser.get().getIsLibrarian();
    }

    @Override
    public void logout() {
        activeRepository.deleteAll();
    }
    
}
