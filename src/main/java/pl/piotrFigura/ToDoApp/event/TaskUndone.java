package pl.piotrFigura.ToDoApp.event;

import java.time.Clock;
import pl.piotrFigura.ToDoApp.task.domain.Task;

public class TaskUndone extends TaskEvent {

    TaskUndone(Task source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
