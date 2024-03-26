package pl.piotrFigura.ToDoApp.task.domain.contract;

import lombok.Getter;
import lombok.Setter;
import pl.piotrFigura.ToDoApp.task.domain.Task;

@Getter
@Setter
class GroupTaskReadModel {

    private String description;
    private boolean done;

    public GroupTaskReadModel() {
    }

    public GroupTaskReadModel(Task source) {
        description = source.getDescription();
        done = source.isDone();
    }
}
