package pl.piotrFigura.ToDoApp.auth;

import pl.piotrFigura.ToDoApp.dto.JwtAuthenticationResponse;
import pl.piotrFigura.ToDoApp.dto.RefreshTokenRequest;
import pl.piotrFigura.ToDoApp.dto.SignUpRequest;
import pl.piotrFigura.ToDoApp.dto.SigninRequest;
import pl.piotrFigura.ToDoApp.user.User;

public interface AuthenticationService {
    User signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SigninRequest signinRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
