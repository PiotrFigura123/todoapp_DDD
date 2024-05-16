package pl.piotrFigura.ToDoApp.task.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import pl.piotrFigura.ToDoApp.util.Audit;
import pl.piotrFigura.ToDoApp.util.Description;

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

    public Task(LocalDateTime deadline, String description) {
        this(description, deadline, null);
    }

    public Task(String description, LocalDateTime deadline, TaskGroups group){
        this.description = description;
        this.deadline = deadline;
        if(group != null){
            this.group =group;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
    public TaskGroups getGroup() {
        return group;
    }

    public void setGroup(TaskGroups taskGroups) {
        this.group = taskGroups;
    }

    public void updateFrom(final Task source){
        deadline = source.deadline;
        done = source.done;
        description = source.description;
        group = source.group;
    }
}
