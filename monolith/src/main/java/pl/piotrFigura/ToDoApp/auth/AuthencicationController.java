package pl.piotrFigura.ToDoApp.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piotrFigura.ToDoApp.auth.dto.JwtAuthenticationResponse;
import pl.piotrFigura.ToDoApp.auth.dto.RefreshTokenRequest;
import pl.piotrFigura.ToDoApp.auth.dto.SignUpRequest;
import pl.piotrFigura.ToDoApp.auth.dto.SigninRequest;
import pl.piotrFigura.ToDoApp.user.User;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
class AuthencicationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest signinRequest){
        return ResponseEntity.ok(authenticationService.signin(signinRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
}
