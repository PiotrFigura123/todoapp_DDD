package pl.piotrFigura.ToDoApp.task.domain.contract;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.domain.TaskGroups;

@Getter
@Setter
public class GroupReadModel {
    private Long id;
    private String description;
    private LocalDateTime deadline;
    private Set<GroupTaskReadModel> tasks;

    public GroupReadModel (TaskGroups taskGroups){
            id=taskGroups.getId();
            description = taskGroups.getDescription();
            taskGroups.getTasks().stream()
                .map(Task::getDeadline)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .ifPresent(date -> deadline = date);
            tasks = taskGroups.getTasks().stream()
                .map(GroupTaskReadModel::new)
                .collect(Collectors.toSet());
    }
}
