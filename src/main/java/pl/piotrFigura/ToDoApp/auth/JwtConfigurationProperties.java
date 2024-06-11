package pl.piotrFigura.ToDoApp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("jwt")
class JwtConfigurationProperties {

    private String secret;
    private long validityInDays;

    public String getSecret() {
        return secret;
    }

    void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getValidityInDays() {
        return validityInDays;
    }

    void setValidityInDays(Long validityInDays) {
        this.validityInDays = validityInDays;
    }
}
