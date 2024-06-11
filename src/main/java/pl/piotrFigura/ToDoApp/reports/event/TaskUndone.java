package pl.piotrFigura.ToDoApp.reports.event;

import java.time.Clock;
import pl.piotrFigura.ToDoApp.task.domain.TaskDto;

public class TaskUndone extends TaskEvent {

    TaskUndone(TaskDto source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
