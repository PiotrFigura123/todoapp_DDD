package pl.piotrFigura.ToDoApp.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.piotrFigura.ToDoApp.user.UserRepository;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService{

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
}
