package pl.piotrFigura.ToDoApp.project.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import java.util.stream.Collectors;
import pl.piotrFigura.ToDoApp.task.domain.TaskGroups;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @OneToMany( cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.EAGER)
    private Set<ProjectSteps> steps;
    @OneToMany(mappedBy = "project")
    private Set<TaskGroups> groups;

    public Project() {
    }

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
    public Set<TaskGroups> getGroups() {
        return groups;
    }
    public void setGroups(Set<TaskGroups> taskGroups) {
        this.groups = taskGroups;
    }
    public Set<ProjectSteps> getSteps() {
        return steps.stream().map(original -> {
            var result = new ProjectSteps(original.getDescription(), original.getDaysToDeadline(), original.getProject());
            result.setId(original.getId());
            return result;
        }).collect(Collectors.toUnmodifiableSet());
    }
    public void addStep(ProjectSteps step){
        if(steps.contains(step)){
            return;
        }
        steps.add(step);
        step.setProject(this);
    }
    public void removeStep(ProjectSteps step){
        if(!steps.contains(step)){
            return;
        }
        steps.remove(step);
        step.setProject(null);

    }
}
