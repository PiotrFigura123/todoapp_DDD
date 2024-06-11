package pl.piotrFigura.ToDoApp.reports.event;

import java.time.Clock;
import java.time.Instant;
import pl.piotrFigura.ToDoApp.task.domain.Task;

public abstract class TaskEvent {
    public static TaskEvent changed(Task source){
        return source.isDone() ? new TaskDone(source) : new TaskUndone(source);
    }
    private Long taskId;
    private Instant occurrence;

    TaskEvent(final Long taskId, Clock clock) {
        this.taskId = taskId;
        this.occurrence = Instant.now(clock);
    }

    public Long getTaskId() {
        return taskId;
    }

    public Instant getOccurrence() {
        return occurrence;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
            "taskId=" + taskId +
            ", occurrence=" + occurrence +
            '}';
    }
}
