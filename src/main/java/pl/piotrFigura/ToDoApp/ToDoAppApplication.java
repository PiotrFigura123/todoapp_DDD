package pl.piotrFigura.ToDoApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import pl.piotrFigura.ToDoApp.user.Role;
import pl.piotrFigura.ToDoApp.user.User;
import pl.piotrFigura.ToDoApp.user.UserRepository;

@EnableConfigurationProperties()
@EnableGlobalAuthentication
@EnableAsync
@SpringBootApplication
public class ToDoAppApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(ToDoAppApplication.class, args);
	}

	@Bean
	Validator validator(){
		return new LocalValidatorFactoryBean();
	}

	public void run(String... args){
		User adminAccount = userRepository.findByRole(Role.ADMIN);
		if(null == adminAccount){
			var user = new User();
			user.setEmail("admin@gmail.com");
			user.setFirstName("admin");
			user.setSecondName("admin");
			user.setRole(Role.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
		}
	}
}
