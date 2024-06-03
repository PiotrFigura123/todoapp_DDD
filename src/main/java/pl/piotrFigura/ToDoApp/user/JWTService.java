package pl.piotrFigura.ToDoApp.user;

import io.jsonwebtoken.Claims;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {

    String extractUserName(String token);

    String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);

    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails user);
}

