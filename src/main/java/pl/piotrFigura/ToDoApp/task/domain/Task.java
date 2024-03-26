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
import lombok.Builder;
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
    @JoinColumn(name = "task_groups_id", referencedColumnName = "id")
    private TaskGroups taskGroups;
    public Task() {
    }

    public Task(String description, LocalDateTime deadline){
        this.description = description;
        this.deadline = deadline;
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
    TaskGroups getTaskGroups() {
        return taskGroups;
    }

    void setTaskGroups(TaskGroups taskGroups) {
        this.taskGroups = taskGroups;
    }

    public void updateFrom(final Task source){
        deadline = source.deadline;
        taskGroups = source.taskGroups;
    }
}
