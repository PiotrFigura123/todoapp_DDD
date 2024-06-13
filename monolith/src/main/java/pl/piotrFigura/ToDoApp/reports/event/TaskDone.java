package pl.piotrFigura.ToDoApp.reports.event;

import java.time.Clock;
import pl.piotrFigura.ToDoApp.task.domain.TaskDto;

public class TaskDone extends TaskEvent {

    TaskDone(TaskDto source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
