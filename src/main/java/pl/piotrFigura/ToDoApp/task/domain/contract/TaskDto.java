package pl.piotrFigura.ToDoApp.task.domain.contract;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import pl.piotrFigura.ToDoApp.task.domain.Task;

public class TaskDto {
    private Long id;
    @NotBlank
    private String description;
    private LocalDateTime deadline;
    private boolean done;

    public TaskDto(Task save) {
        id = save.getId();
        description = save.getDescription();
        deadline = save.getDeadline();
        done = save.isDone();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
