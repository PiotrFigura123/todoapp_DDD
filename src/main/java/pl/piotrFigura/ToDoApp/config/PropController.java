package pl.piotrFigura.ToDoApp.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PropController {

    private DataSourceProperties dataSourceProperties;
    private TaskConfigurationProperties taskConfigurationProperties;

    public PropController(DataSourceProperties dataSourceProperties, TaskConfigurationProperties taskConfigurationProperties) {
        this.dataSourceProperties = dataSourceProperties;
        this.taskConfigurationProperties = taskConfigurationProperties;
    }

    @GetMapping("/info/prop")
    boolean prop(){
        return taskConfigurationProperties.isAllowMultipleTaskFromTemplate();
    }

    @GetMapping("/info/url")
    String url(){
        return dataSourceProperties.getUrl();
    }
}