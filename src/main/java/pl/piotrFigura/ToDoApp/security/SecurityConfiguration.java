package pl.piotrFigura.ToDoApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
class SecurityConfiguration {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests(authorizeRequests ->
//                authorizeRequests
////                    .requestMatchers("**").permitAll()
////                    .requestMatchers("/info/prop").hasAnyRole("USER")
////                    .requestMatchers("/info/url").hasAnyRole("ADMIN")
//                    .anyRequest().permitAll()
//            )
//            .oauth2ResourceServer(oauth2 -> oauth2.jwt())
//            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        return http.build();
//    }
}
