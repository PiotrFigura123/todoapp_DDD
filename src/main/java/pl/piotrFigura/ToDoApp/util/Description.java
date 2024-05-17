package pl.piotrFigura.ToDoApp.util;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import pl.piotrFigura.ToDoApp.event.TaskEvent;

@MappedSuperclass
public abstract class Description {

    @NotBlank(message = "description can't be blank")
    protected String description;
    protected boolean done;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }


}
