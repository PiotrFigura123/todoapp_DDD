package pl.piotrFigura.ToDoApp.event;

import java.time.Clock;
import pl.piotrFigura.ToDoApp.task.domain.Task;

public class TaskDone extends TaskEvent {

    TaskDone(Task source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
