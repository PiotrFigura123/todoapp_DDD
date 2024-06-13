package pl.piotrFigura.ToDoApp.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.piotrFigura.ToDoApp.user.Role;
import pl.piotrFigura.ToDoApp.user.User;
import pl.piotrFigura.ToDoApp.user.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService, CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username)  {
                return userRepository.findByEmail(username)
                    .orElseThrow(()-> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void run(String... args){
        User adminAccount = userRepository.findByRole(Role.ADMIN);
        if(null == adminAccount){
            var user = new User();
            user.setEmail("admin@gmail.com");
            user.setFirstName("admin");
            user.setSecondName("admin");
            user.setRole(Role.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }
    }
}
