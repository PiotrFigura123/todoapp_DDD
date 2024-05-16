package pl.piotrFigura.ToDoApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@EnableConfigurationProperties()
@SpringBootApplication
public class ToDoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoAppApplication.class, args);
	}

	@Bean
	Validator validator(){
		return new LocalValidatorFactoryBean();
	}
}
