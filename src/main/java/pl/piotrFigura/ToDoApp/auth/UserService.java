package pl.piotrFigura.ToDoApp.auth;

import org.springframework.security.core.userdetails.UserDetailsService;

interface UserService {

   UserDetailsService userDetailsService();

}
