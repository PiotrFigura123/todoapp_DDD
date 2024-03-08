package pl.piotrFigura.ToDoApp.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PropController {

    private DataSourceProperties dataSourceProperties;
    private MyProperties myProperties;

    public PropController(DataSourceProperties dataSourceProperties, MyProperties myProperties) {
        this.dataSourceProperties = dataSourceProperties;
        this.myProperties = myProperties;
    }

    @GetMapping("/info/prop")
    boolean prop(){
        return myProperties.isAllowMultipleTaskFromTemplate();
    }

    @GetMapping("/info/url")
    String url(){
        return dataSourceProperties.getUrl();
    }
}