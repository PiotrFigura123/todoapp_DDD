package pl.piotrFigura.ToDoApp.task.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import pl.piotrFigura.ToDoApp.task.TaskGroups;

public class GroupReadModel {
    private Long id;
    private String description;
    private LocalDateTime deadline;
    private Set<GroupTaskReadModel> tasks;

    public GroupReadModel (TaskGroups taskGroups){
            id=taskGroups.getId();
            description = taskGroups.getDescription();
            taskGroups.getTasks().stream()
                .map(task ->
                    task.getDeadline())
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .ifPresent(date -> deadline = date);
            tasks = taskGroups.getTasks().stream()
                .map(task -> new GroupTaskReadModel(TaskDto.builder()
                        .withDeadline(task.getDeadline())
                        .withDone(task.isDone())
                        .withDescription(task.getDescription())
                        .withId(task.getId())
                        .build()))
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Set<GroupTaskReadModel> getTasks() {
        return tasks;
    }

    public void setTasks(Set<GroupTaskReadModel> tasks) {
        this.tasks = tasks;
    }
}
