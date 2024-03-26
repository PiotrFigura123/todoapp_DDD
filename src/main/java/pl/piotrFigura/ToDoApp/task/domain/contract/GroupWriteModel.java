package pl.piotrFigura.ToDoApp.task.domain.contract;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import pl.piotrFigura.ToDoApp.task.domain.TaskGroups;

@Getter
@Setter
public class GroupWriteModel {

private String description;
private Set<GroupTaskWriteModel> tasks;


    public TaskGroups toGroup(){
        return new TaskGroups(description, tasks.stream()
            .map(GroupTaskWriteModel::toTask)
            .collect(Collectors.toSet()));
    }
}
