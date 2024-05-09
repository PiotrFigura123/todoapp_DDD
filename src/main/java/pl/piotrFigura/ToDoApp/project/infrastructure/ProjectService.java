package pl.piotrFigura.ToDoApp.project.infrastructure;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import pl.piotrFigura.ToDoApp.config.TaskConfigurationProperties;
import pl.piotrFigura.ToDoApp.project.domain.Project;
import pl.piotrFigura.ToDoApp.project.infrastructure.jpa.ProjectRepository;
import java.util.List;
import pl.piotrFigura.ToDoApp.task.domain.contract.GroupReadModel;
import pl.piotrFigura.ToDoApp.task.domain.contract.GroupTaskWriteModel;
import pl.piotrFigura.ToDoApp.task.domain.contract.GroupWriteModel;
import pl.piotrFigura.ToDoApp.task.infrastructure.TaskGroupService;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskGroupRepository;


@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;
    private TaskGroupService taskGroupService;

    public ProjectService(ProjectRepository projectRepository, TaskGroupRepository taskGroupRepository,
        TaskConfigurationProperties taskConfigurationProperties, TaskGroupService taskGroupService) {
        this.projectRepository = projectRepository;
        this.taskGroupRepository = taskGroupRepository;
        this.config = taskConfigurationProperties;
        this.taskGroupService = taskGroupService;
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
        GroupReadModel result = projectRepository.findById(projectId)
            .map(project -> {
                var targetGroup = new GroupWriteModel();
                targetGroup.setDescription(project.getDescription());
                targetGroup.setTasks(
                    project.getSteps().stream()
                    .map(projectStep -> {
                        var task = new GroupTaskWriteModel();
                        task.setDescription(projectStep.getDescription());
                        task.setDeadline(deadline.plusDays(projectStep.getDaysToDeadline()));
                        return task;
                    }
                    ).collect(Collectors.toSet())
                );
                return taskGroupService.crateGroup(targetGroup);
            }).orElseThrow(()-> new IllegalArgumentException("Project with given id not found"));
        return result;
    }
}
