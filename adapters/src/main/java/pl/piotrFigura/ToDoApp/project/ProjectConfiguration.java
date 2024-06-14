package pl.piotrFigura.ToDoApp.project;

import org.springframework.context.annotation.Bean;
import pl.piotrFigura.ToDoApp.task.TaskConfigurationProperties;
import pl.piotrFigura.ToDoApp.task.TaskGroupFacade;
import pl.piotrFigura.ToDoApp.task.TaskQueryGroupRepository;

class ProjectConfiguration {

    @Bean
    ProjectFacade projectFacade(final TaskGroupFacade taskGroupFacade,
                                final ProjectRepository projectRepository,
                                final TaskConfigurationProperties config,
                                final TaskQueryGroupRepository taskQueryGroupRepository) {
        return new ProjectFacade(taskGroupFacade, projectRepository, config, taskQueryGroupRepository);
    }
}
