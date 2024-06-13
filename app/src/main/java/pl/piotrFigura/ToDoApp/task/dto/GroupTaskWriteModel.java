package pl.piotrFigura.ToDoApp.task.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import pl.piotrFigura.ToDoApp.task.Task;
import pl.piotrFigura.ToDoApp.task.TaskGroups;

public class GroupTaskWriteModel {
    @NotBlank(message = "description can't be blank")
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deadline;

    public Task toTask(TaskGroups group){
        return new Task(description, deadline, group);
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
}
