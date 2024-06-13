package pl.piotrFigura.ToDoApp.task;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import pl.piotrFigura.ToDoApp.task.dto.TaskDto;

import java.time.LocalDateTime;


@Entity
@Table(name = "tasks")
public class Task extends Description {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime deadline;

    @Embedded
    private Audit audit = new Audit();

    @ManyToOne
    @JoinColumn(name = "task_groups_id")
    private TaskGroups group;

    public Task() {
    }

    public Task(LocalDateTime deadline, @NotNull String description) {

        this(description, deadline, null);
    }

    public Task(String description, LocalDateTime deadline, TaskGroups group){
        this.description = description;
        this.deadline = deadline;
        if(group != null){
            this.group =group;
        }
    }

    private TaskDto toDto(Task task){
        return TaskDto.builder()
                .withId(task.getId())
                .withDescription(task.getDescription())
                .withDone(task.isDone())
                .withDeadline(task.getDeadline())
                .build();
    }
    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }


    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
    TaskGroups getGroup() {
        return group;
    }

    void setGroup(TaskGroups taskGroups) {
        this.group = taskGroups;
    }

    public void updateFrom(final Task source){
        deadline = source.deadline;
        done = source.done;
        description = source.description;
        group = source.group;
    }

}
