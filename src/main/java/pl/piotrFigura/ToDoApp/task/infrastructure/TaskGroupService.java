package pl.piotrFigura.ToDoApp.task.infrastructure;

import org.springframework.stereotype.Service;
import pl.piotrFigura.ToDoApp.task.domain.TaskGroups;
import pl.piotrFigura.ToDoApp.task.domain.contract.GroupReadModel;
import pl.piotrFigura.ToDoApp.task.domain.contract.GroupWriteModel;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskGroupRepository;
import java.util.List;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskRepository;

@Service
public class TaskGroupService {

    private TaskGroupRepository taskGroupRepository;
    private TaskRepository taskRepository;

    public GroupReadModel crateGroup(GroupWriteModel source){
       TaskGroups result = taskGroupRepository.save(source.toGroup());
       return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll(){
        return taskGroupRepository.findAll().stream()
            .map(GroupReadModel::new)
            .toList();
    }

    public void toggleGroup(Long groupId){
        if(taskRepository.existsByTaskGroup(true, groupId)){
            throw new IllegalArgumentException("cant be close. All task must be done");
        }
        TaskGroups result = taskGroupRepository.findById(groupId).orElseThrow(()-> new IllegalArgumentException("TaskGroup with id not found"));
        result.setDone(!result.isDone());
    }
}
