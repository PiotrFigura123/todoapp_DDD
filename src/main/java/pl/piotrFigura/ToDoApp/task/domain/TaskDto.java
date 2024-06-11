package pl.piotrFigura.ToDoApp.task.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@JsonDeserialize(builder = TaskDto.Builder.class)
public class TaskDto {
    public static Builder builder(){
        return new Builder();
    }
    private final Long id;
    @NotBlank
    private final String description;
    private final LocalDateTime deadline;
    private final boolean done;

    private TaskDto(final Builder builder) {
        id = builder.id;
        description = builder.description;
        deadline = builder.deadline;
        done = builder.done;
    }
    public Builder toBuilder(){
        return builder()
                .withId(id)
                .withDescription(description)
                .withDone(done)
                .withDeadline(deadline);
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

    @JsonPOJOBuilder
    public static class Builder{
        private Long id;
        @NotBlank
        private String description;
        private LocalDateTime deadline;
        private boolean done;
        public TaskDto build(){
            return new TaskDto(this);
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
