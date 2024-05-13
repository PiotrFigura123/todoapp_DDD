package pl.piotrFigura.ToDoApp.project.infrastructure;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import pl.piotrFigura.ToDoApp.config.TaskConfigurationProperties;
import pl.piotrFigura.ToDoApp.project.domain.Project;
import pl.piotrFigura.ToDoApp.project.domain.contract.ProjectWriteModel;
import pl.piotrFigura.ToDoApp.project.infrastructure.jpa.ProjectRepository;
import java.util.List;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.domain.TaskGroups;
import pl.piotrFigura.ToDoApp.task.domain.contract.GroupReadModel;
import pl.piotrFigura.ToDoApp.task.domain.contract.GroupTaskWriteModel;
import pl.piotrFigura.ToDoApp.task.domain.contract.GroupWriteModel;
import pl.piotrFigura.ToDoApp.task.infrastructure.TaskGroupService;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskGroupRepository;


@Service
public class ProjectService {

    public ProjectService(TaskGroupService taskGroupService, ProjectRepository projectRepository,
        TaskGroupRepository taskGroupRepository, TaskConfigurationProperties config) {
        this.taskGroupService = taskGroupService;
        this.projectRepository = projectRepository;
        this.taskGroupRepository = taskGroupRepository;
        this.config = config;
    }

    private TaskGroupService taskGroupService;
    private ProjectRepository projectRepository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;

    public List<Project> readAll(){
        return projectRepository.findAll();
    }

    public Project create(final ProjectWriteModel project){

        return projectRepository.save(project.toProject());
    }

    public GroupReadModel createGroup(LocalDateTime deadline, Long projectId){
        if(!config.isAllowMultipleTaskFromTemplate() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)){
            throw new IllegalStateException("Only one undone group from project is allowed");
        }
        return projectRepository.findById(projectId)
            .map(project -> {
                var targetGroup = new GroupWriteModel();
                targetGroup.setDescription(project.getDescription());
                targetGroup.setTasks(
                    project.getSteps().stream()
                        .map(projectSteps -> {
                            var task = new GroupTaskWriteModel();
                            task.setDescription(projectSteps.getDescription());
                            task.setDeadline(deadline.plusDays(projectSteps.getDaysToDeadline()));
                            return task;
                        }).collect(Collectors.toSet())
                );
                return taskGroupService.crateGroup(targetGroup, project);
            }).orElseThrow(()-> new IllegalArgumentException("Project with given id not found"));
    }
}
