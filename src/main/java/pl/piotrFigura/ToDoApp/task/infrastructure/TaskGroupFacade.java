package pl.piotrFigura.ToDoApp.task.infrastructure;

import java.util.List;
import org.springframework.stereotype.Service;
import pl.piotrFigura.ToDoApp.project.query.SimpleProjectQueryDto;
import pl.piotrFigura.ToDoApp.task.domain.TaskGroups;
import pl.piotrFigura.ToDoApp.task.domain.contract.GroupReadModel;
import pl.piotrFigura.ToDoApp.task.domain.contract.GroupWriteModel;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskGroupRepository;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskQueryGroupRepository;

@Service
//@RequestScope
public class TaskGroupFacade {

    private final TaskGroupRepository taskGroupRepository;
    private final TaskQueryGroupRepository taskQueryGroupRepository;
    private final TaskFacade taskFacade;

    public TaskGroupFacade(TaskGroupRepository taskGroupRepository, TaskQueryGroupRepository taskQueryGroupRepository, TaskFacade taskFacade) {
        this.taskGroupRepository = taskGroupRepository;
        this.taskQueryGroupRepository = taskQueryGroupRepository;
        this.taskFacade = taskFacade;
    }

    public GroupReadModel crateGroup(GroupWriteModel source){
       return crateGroup(source, null);
    }

    public GroupReadModel crateGroup(GroupWriteModel source, SimpleProjectQueryDto project) {
       TaskGroups result = taskGroupRepository.save(source.toGroup(project));
    return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll(){
        return taskQueryGroupRepository.findAll().stream()
                .map(GroupReadModel::new)
                .toList();
    }

    public void toggleGroup(Long groupId){
        if(taskFacade.areUndoneTasksWithGroup(groupId)){
            throw new IllegalStateException("cant be close. All task must be done");
        }
        TaskGroups result = taskGroupRepository.findById(groupId)
            .orElseThrow(()-> new IllegalArgumentException("TaskGroup with id not found"));
        result.setDone(!result.isDone());
        taskGroupRepository.save(result);
    }

    public boolean areUndoneTasksWithProjectId(Long projectId) {
        return taskQueryGroupRepository.existsByDoneIsFalseAndProject_Id(projectId);
    }

}
