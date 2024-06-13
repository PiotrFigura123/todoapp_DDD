package pl.piotrFigura.ToDoApp.auth;

import pl.piotrFigura.ToDoApp.auth.dto.JwtAuthenticationResponse;
import pl.piotrFigura.ToDoApp.auth.dto.RefreshTokenRequest;
import pl.piotrFigura.ToDoApp.auth.dto.SignUpRequest;
import pl.piotrFigura.ToDoApp.auth.dto.SigninRequest;
import pl.piotrFigura.ToDoApp.user.User;


interface AuthenticationService {
    User signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SigninRequest signinRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
