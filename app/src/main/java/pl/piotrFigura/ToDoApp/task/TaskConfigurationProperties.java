package pl.piotrFigura.ToDoApp.task;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(value = "task")
public class TaskConfigurationProperties {

    private boolean allowMultipleTaskFromTemplate;

    public boolean isAllowMultipleTaskFromTemplate() {
        return allowMultipleTaskFromTemplate;
    }

    public void setAllowMultipleTaskFromTemplate(boolean allowMultipleTaskFromTemplate) {
        this.allowMultipleTaskFromTemplate = allowMultipleTaskFromTemplate;
    }
}
