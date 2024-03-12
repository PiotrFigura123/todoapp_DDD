package pl.piotrFigura.ToDoApp.project;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import pl.piotrFigura.ToDoApp.task.TaskGroups;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "project")
    private Set<ProjectSteps> steps;

    @OneToMany(mappedBy = "project")
    private Set<TaskGroups> taskGroups;

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

    public Set<TaskGroups> getTaskGroups() {
        return taskGroups;
    }

    public void setTaskGroups(Set<TaskGroups> taskGroups) {
        this.taskGroups = taskGroups;
    }

    public Set<ProjectSteps> getSteps() {
        return steps;
    }

    public void setSteps(Set<ProjectSteps> project) {
        this.steps = project;
    }
}
