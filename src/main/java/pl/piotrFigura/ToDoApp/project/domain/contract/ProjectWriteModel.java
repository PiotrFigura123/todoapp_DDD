package pl.piotrFigura.ToDoApp.project.domain.contract;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import lombok.Getter;
import lombok.Setter;
import pl.piotrFigura.ToDoApp.project.domain.Project;
import pl.piotrFigura.ToDoApp.project.domain.ProjectSteps;
import java.util.List;

@Getter
@Setter
public class ProjectWriteModel {
    @NotNull(message = "Project description must not be empty")
    private String description;

    @Valid
    private List<ProjectSteps> steps;

    public Project toProject(){
        var result = new Project();
        result.setDescription(description);
        steps.forEach(step -> step.setProject(result));
        result.setSteps(new HashSet<>(steps));
        return result;
    }
}
