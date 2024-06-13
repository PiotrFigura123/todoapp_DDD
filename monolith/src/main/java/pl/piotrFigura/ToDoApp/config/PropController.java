package pl.piotrFigura.ToDoApp.config;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piotrFigura.ToDoApp.task.TaskConfigurationProperties;

@RestController
class PropController {

    private DataSourceProperties dataSourceProperties;
    private TaskConfigurationProperties taskConfigurationProperties;

    public PropController(DataSourceProperties dataSourceProperties, TaskConfigurationProperties taskConfigurationProperties) {
        this.dataSourceProperties = dataSourceProperties;
        this.taskConfigurationProperties = taskConfigurationProperties;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info/prop")
    boolean prop(){
        return taskConfigurationProperties.isAllowMultipleTaskFromTemplate();
    }

    @RolesAllowed({"ROLE_USERS", "ROLE_ADMIN"})
    @GetMapping("/info/url")
    String url(){
        return dataSourceProperties.getUrl();
    }
}