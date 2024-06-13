package pl.piotrFigura.ToDoApp.task.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pl.piotrFigura.ToDoApp.project.SimpleProjectQueryDto;
import pl.piotrFigura.ToDoApp.task.TaskGroups;

public class GroupWriteModel {

    @NotBlank(message = "description can't be empty")
    private String description;
    @Valid
    private List<GroupTaskWriteModel> tasks = new ArrayList<>();

    public GroupWriteModel() {
        tasks.add(new GroupTaskWriteModel());
    }
    public TaskGroups toGroup(SimpleProjectQueryDto project){
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GroupTaskWriteModel> getTasks() {
        return tasks;
    }

    public void setTasks(List<GroupTaskWriteModel> tasks) {
        this.tasks = tasks;
    }
}
