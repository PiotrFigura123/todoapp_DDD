package pl.piotrFigura.ToDoApp.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TaskGroupConfiguration {
    @Bean
    TaskGroupFacade taskGroupFacade(final TaskGroupRepository taskGroupRepository,
                                    final TaskQueryGroupRepository taskQueryGroupRepository,
                                    final TaskQueryRepository taskQueryRepository) {
        return new TaskGroupFacade(taskGroupRepository,taskQueryGroupRepository, taskQueryRepository );
    }
}
