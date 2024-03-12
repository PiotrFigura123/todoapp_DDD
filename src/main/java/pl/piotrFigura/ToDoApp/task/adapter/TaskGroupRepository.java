package pl.piotrFigura.ToDoApp.task.adapter;

import java.util.List;
import java.util.Optional;
import pl.piotrFigura.ToDoApp.task.TaskGroups;

public interface TaskGroupRepository {

    List<TaskGroups> findAll();

    Optional<TaskGroups> findById(Long id);

    TaskGroups save(TaskGroups entity);

}
