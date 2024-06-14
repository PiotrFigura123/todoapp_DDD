package pl.piotrFigura.ToDoApp.task;

import java.util.List;
import pl.piotrFigura.ToDoApp.project.SimpleProjectQueryDto;
import pl.piotrFigura.ToDoApp.task.dto.GroupReadModel;
import pl.piotrFigura.ToDoApp.task.dto.GroupWriteModel;


public class TaskGroupFacade {

    private final TaskGroupRepository taskGroupRepository;
    private final TaskQueryGroupRepository taskQueryGroupRepository;
    private final TaskQueryRepository taskQueryRepository;

    public TaskGroupFacade(final TaskGroupRepository taskGroupRepository,
                           final TaskQueryGroupRepository taskQueryGroupRepository,
                           final TaskQueryRepository taskQueryRepository) {
        this.taskGroupRepository = taskGroupRepository;
        this.taskQueryGroupRepository = taskQueryGroupRepository;
        this.taskQueryRepository = taskQueryRepository;
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
        if(taskQueryRepository.existsByDoneIsFalseAndGroup_Id( groupId)){
            throw new IllegalStateException("cant be close. All task must be done");
        }
        TaskGroups result = taskGroupRepository.findById(groupId)
            .orElseThrow(()-> new IllegalArgumentException("TaskGroup with id not found"));
        result.setDone(!result.isDone());
        taskGroupRepository.save(result);
    }
}
