package pl.piotrFigura.ToDoApp.auth;

import io.jsonwebtoken.Claims;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;

interface JWTService {

    String extractUserName(String token);

    String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);

    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails user);
}

