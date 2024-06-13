package pl.piotrFigura.ToDoApp.task.dto;

import jakarta.validation.constraints.NotBlank;
import pl.piotrFigura.ToDoApp.report.TaskEvent;

import java.time.LocalDateTime;

public class TaskDto {
    public static Builder builder(){
        return new Builder();
    }
    private final Long id;
    @NotBlank
    private final String description;
    private final LocalDateTime deadline;
    private boolean done;

    public TaskDto(final Long id, final String description, final LocalDateTime deadline, final boolean done) {
        this.id = id;
        this.description = description;
        this.deadline = deadline;
        this.done = done;
    }

    public Builder toBuilder(){
        return new Builder()
                .withId(id)
                .withDescription(description)
                .withDone(done)
                .withDeadline(deadline);
    }

    public TaskEvent toggle() {
        this.done = !this.done;
        toBuilder().build();
        return TaskEvent.changed(this);
    }
    public Long getId() {
        return id;
    }


    public String getDescription() {
        return description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public boolean isDone() {
        return done;
    }

    public static class Builder{
        private Long id;
        @NotBlank
        private String description;
        private LocalDateTime deadline;
        private boolean done;
        public TaskDto build(){

            return new TaskDto(id, description, deadline, done);
        }
        private Builder(){
        }

        public Builder withId(final Long id) {
            this.id = id;
            return this;
        }

        public Builder withDescription(final String description) {
            this.description = description;
            return this;
        }

        public Builder withDeadline(final LocalDateTime deadline) {
            this.deadline = deadline;
            return this;
        }

        public Builder withDone(final boolean done) {
            this.done = done;
            return this;
        }
    }
}
