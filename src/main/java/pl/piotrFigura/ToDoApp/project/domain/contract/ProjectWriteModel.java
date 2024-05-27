package pl.piotrFigura.ToDoApp.project.domain.contract;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import pl.piotrFigura.ToDoApp.project.domain.Project;
import pl.piotrFigura.ToDoApp.project.domain.ProjectSteps;

public class ProjectWriteModel {
    @NotBlank(message = "Project description must not be empty")
    private String description;

    @Valid
    private List<ProjectSteps> steps = new ArrayList<>();

    public ProjectWriteModel(){
        steps.add(new ProjectSteps());
    }

    public Project toProject(){
        var result = new Project();
        result.setDescription(description);
        steps.forEach(step -> step.setProject(result));
        steps.forEach(step -> result.addStep(step));
        return result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProjectSteps> getSteps() {
        return steps;
    }

    public void setSteps(List<ProjectSteps> steps) {
        this.steps = steps;
    }
}
