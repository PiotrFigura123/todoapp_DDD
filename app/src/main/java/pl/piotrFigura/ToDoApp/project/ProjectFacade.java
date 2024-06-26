package pl.piotrFigura.ToDoApp.project;

import org.springframework.stereotype.Service;

import pl.piotrFigura.ToDoApp.project.dto.ProjectWriteModel;
import pl.piotrFigura.ToDoApp.task.TaskConfigurationProperties;
import pl.piotrFigura.ToDoApp.task.TaskGroupFacade;
import pl.piotrFigura.ToDoApp.task.TaskQueryGroupRepository;
import pl.piotrFigura.ToDoApp.task.dto.GroupReadModel;
import pl.piotrFigura.ToDoApp.task.dto.GroupTaskWriteModel;
import pl.piotrFigura.ToDoApp.task.dto.GroupWriteModel;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectFacade {

    private TaskGroupFacade taskGroupFacade;
    private ProjectRepository projectRepository;
    private TaskConfigurationProperties config;
    private final TaskQueryGroupRepository taskQueryGroupRepository;

    public ProjectFacade(final TaskGroupFacade taskGroupFacade, final ProjectRepository projectRepository,
                         final TaskConfigurationProperties config, final TaskQueryGroupRepository taskQueryGroupRepository) {
        this.taskGroupFacade = taskGroupFacade;
        this.projectRepository = projectRepository;
        this.config = config;
        this.taskQueryGroupRepository = taskQueryGroupRepository;
    }


    public Project save(final ProjectWriteModel project){
        return projectRepository.save(project.toProject());
    }

    public Project saveWithId(Project toSave){
        return projectRepository.findById(toSave.getId())
            .map(existingProject -> {
                existingProject.setDescription(toSave.getDescription());
                Set<ProjectSteps> stepToRemowe = new HashSet<>();
                existingProject.getSteps()
                    .forEach(existingStep -> toSave.getSteps().stream()
                        .filter(potentialOverride -> existingStep.getId() == potentialOverride.getId())
                        .findFirst()
                        .ifPresentOrElse(
                            overrideStep -> {
                                existingStep.setDaysToDeadline(overrideStep.getDaysToDeadline());
                                existingStep.setDescription(overrideStep.getDescription());
                            },
                            () -> stepToRemowe.add(existingStep)
                        ));
            stepToRemowe.forEach(toRemove -> {
                existingProject.removeStep(toRemove);
            });
            toSave.getSteps().stream()
                .filter(newStep -> existingProject.getSteps().stream()
                    .noneMatch(existingStep -> existingStep.getId() == newStep.getId())
                ).collect(Collectors.toSet())
                .forEach(existingProject::addStep);
            projectRepository.save(existingProject);
            return existingProject;
            }).orElseGet(()-> {
                toSave.getSteps().forEach(step -> {
                    if (step.getProject() == null) {
                        step.setProject(toSave);
                    }
                });
                return projectRepository.save(toSave);
            });
    }

    public GroupReadModel createGroup(LocalDateTime deadline, Long projectId){
        if(!config.isAllowMultipleTaskFromTemplate() && taskQueryGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)){
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
                        }).toList()
                );
                var simpleProject = new SimpleProjectQueryDto(project.getId(), project.getDescription());
                return taskGroupFacade.crateGroup(targetGroup, simpleProject);
            }).orElseThrow(()-> new IllegalArgumentException("Project with given id not found"));
    }
}
