package pl.piotrFigura.ToDoApp.shared;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    public static final String APP_TITLE = "ToDoAoo";
    public static final String APP_DESCRIPTION = "to do app ";
    public static final String APP_VERSION = "1.0";
    private static final String SCHEME_NAME = "bearerAuth";
    private static final String SCHEME = "bearer";
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(getInfo())
            .components(new Components()
                .addSecuritySchemes(SCHEME_NAME, createSecurityScheme()))
            .addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME));
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
            .name(SCHEME_NAME)
            .type(SecurityScheme.Type.HTTP)
            .scheme(SCHEME);
    }
    private Info getInfo() {
        return new Info()
            .title(APP_TITLE)
            .description(APP_DESCRIPTION)
            .version(APP_VERSION)
            .license(new License());
    }
}
