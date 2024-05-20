package pl.piotrFigura.ToDoApp.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SsoController {
@GetMapping("/logout")
    void logout(HttpServletRequest httpServletRequest) throws ServletException {
    httpServletRequest.logout();
}
}
