package pl.piotrFigura.ToDoApp.project.infrastructure;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import pl.piotrFigura.ToDoApp.config.TaskConfigurationProperties;
import pl.piotrFigura.ToDoApp.project.domain.Project;
import pl.piotrFigura.ToDoApp.project.infrastructure.jpa.ProjectRepository;
import java.util.List;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.domain.TaskGroups;
import pl.piotrFigura.ToDoApp.task.domain.contract.GroupReadModel;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskGroupRepository;


@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;

    public ProjectService(ProjectRepository projectRepository, TaskGroupRepository taskGroupRepository,
        TaskConfigurationProperties taskConfigurationProperties) {
        this.projectRepository = projectRepository;
        this.taskGroupRepository = taskGroupRepository;
        this.config = taskConfigurationProperties;
    }

    public List<Project> readAll(){
        return projectRepository.findAll();
    }

    public Project create(final Project project){
        return projectRepository.save(project);
    }

    public GroupReadModel createGroup(LocalDateTime deadline, Long projectId){
        if(!config.isAllowMultipleTaskFromTemplate() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)){
            throw new IllegalStateException("Only one undone group from project is allowed");
        }
        TaskGroups result = projectRepository.findById(projectId)
            .map(project -> {
                var targetGroup = new TaskGroups();
                targetGroup.setDescription(project.getDescription());
                targetGroup.setTasks(project.getSteps().stream()
                    .map(projectStep ->
                        new Task(
                            projectStep.getDescription(),
                            deadline.plusDays(projectStep.getDaysToDeadline()))
                    ).collect(Collectors.toSet())
                );
                targetGroup.setProject(project);
            return taskGroupRepository.save(targetGroup);
            }).orElseThrow(()-> new IllegalArgumentException("Project with given id not found"));
        return new GroupReadModel(result);
    }
}
