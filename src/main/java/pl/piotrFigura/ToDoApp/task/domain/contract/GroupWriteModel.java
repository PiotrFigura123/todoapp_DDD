package pl.piotrFigura.ToDoApp.task.domain.contract;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import pl.piotrFigura.ToDoApp.project.domain.Project;
import pl.piotrFigura.ToDoApp.task.domain.TaskGroups;

@Getter
@Setter
public class GroupWriteModel {

private String description;
private Set<GroupTaskWriteModel> tasks;

    public TaskGroups toGroup(Project project){
        var result = new TaskGroups();
        result.setDescription(description);
        result.setTasks(
            tasks.stream()
                .map(source -> source.toTask(result))
                .collect(Collectors.toSet())
        );
        result.setProject(project);
        return result;
    }
}
