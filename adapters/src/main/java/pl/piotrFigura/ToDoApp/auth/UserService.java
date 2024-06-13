package pl.piotrFigura.ToDoApp.auth;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.piotrFigura.ToDoApp.user.User;

import java.util.Optional;

interface UserService {

   UserDetailsService userDetailsService();

    User saveUser(User user);

    Optional<User> findByEmail(String email);
}

