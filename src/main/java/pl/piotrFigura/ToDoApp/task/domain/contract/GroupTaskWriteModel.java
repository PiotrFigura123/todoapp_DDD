package pl.piotrFigura.ToDoApp.task.domain.contract;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import pl.piotrFigura.ToDoApp.task.domain.Task;

@Getter
@Setter
class GroupTaskWriteModel {

    private String description;
    private LocalDateTime deadline;

    public Task toTask(){
        return new Task(description, deadline);
    }
}
