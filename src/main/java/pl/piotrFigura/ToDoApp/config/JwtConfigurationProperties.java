package pl.piotrFigura.ToDoApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("jwt")
public class JwtConfigurationProperties {

    private String secret;
    private long validityInDays;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getValidityInDays() {
        return validityInDays;
    }

    public void setValidityInDays(Long validityInDays) {
        this.validityInDays = validityInDays;
    }
}
