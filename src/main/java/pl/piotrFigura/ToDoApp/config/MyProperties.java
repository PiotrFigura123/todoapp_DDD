package pl.piotrFigura.ToDoApp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(value = "task")
class MyProperties {

    private boolean allowMultipleTaskFromTemplate;

    public boolean isAllowMultipleTaskFromTemplate() {
        return allowMultipleTaskFromTemplate;
    }

    public void setAllowMultipleTaskFromTemplate(boolean allowMultipleTaskFromTemplate) {
        this.allowMultipleTaskFromTemplate = allowMultipleTaskFromTemplate;
    }
}
