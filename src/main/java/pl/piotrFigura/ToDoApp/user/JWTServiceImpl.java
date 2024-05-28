package pl.piotrFigura.ToDoApp.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
class JWTServiceImpl {

    private String generateToken(UserDetails userDetails){
        return Jwts.builder.
    }
}
