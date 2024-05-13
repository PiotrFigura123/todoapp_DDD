package pl.piotrFigura.ToDoApp.task.domain.contract;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.domain.TaskGroups;

@Getter
@Setter
public class GroupTaskWriteModel {

    private String description;
    private LocalDateTime deadline;

    public Task toTask(TaskGroups group){

        return new Task(description, deadline, group);
    }
}
