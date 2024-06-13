package pl.piotrFigura.ToDoApp.report;

import java.time.Clock;
import pl.piotrFigura.ToDoApp.task.dto.TaskDto;

class TaskUndone extends TaskEvent {

    TaskUndone(TaskDto source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
