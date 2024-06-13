package pl.piotrFigura.ToDoApp.report;

import java.time.Clock;
import pl.piotrFigura.ToDoApp.task.dto.TaskDto;

class TaskDone extends TaskEvent {

    TaskDone(TaskDto source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
