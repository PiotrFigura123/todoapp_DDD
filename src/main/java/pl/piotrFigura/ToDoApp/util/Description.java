package pl.piotrFigura.ToDoApp.util;

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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
