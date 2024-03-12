package pl.piotrFigura.ToDoApp.task;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@MappedSuperclass
public abstract class Description {

    @NotBlank(message = "description can't be blank")
    @NotEmpty(message = "description can't be empty")
    protected String description;
    protected boolean done;

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    boolean isDone() {
        return done;
    }

    void setDone(boolean done) {
        this.done = done;
    }
}
