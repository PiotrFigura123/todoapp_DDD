package pl.piotrFigura.ToDoApp.task;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TaskConfiguration {

    @Bean
    TaskFacade taskFacade(final TaskRepository taskRepository , final ApplicationEventPublisher publisher){
        return new TaskFacade(taskRepository,publisher);
    }
}
