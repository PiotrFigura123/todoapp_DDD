package pl.piotrFigura.ToDoApp.report;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "task_events")
class PersistedTaskEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long taskId;
    LocalDateTime occurrence;
    String name;

    public PersistedTaskEvent() {
    }
    PersistedTaskEvent(TaskEvent taskEvent) {
        taskId = taskEvent.getTaskId();
        name = taskEvent.getClass().getSimpleName();
        occurrence = LocalDateTime.ofInstant(taskEvent.getOccurrence(), ZoneId.systemDefault());
    }

}
