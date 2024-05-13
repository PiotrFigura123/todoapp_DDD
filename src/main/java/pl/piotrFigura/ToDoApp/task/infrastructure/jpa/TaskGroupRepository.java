package pl.piotrFigura.ToDoApp.task.infrastructure.jpa;

import java.util.List;
import java.util.Optional;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.domain.TaskGroups;

public interface TaskGroupRepository {

    List<TaskGroups> findAll();

    Optional<TaskGroups> findById(Long id);

    TaskGroups save(TaskGroups entity);

    boolean existsByDoneIsFalseAndProject_Id(Long id);
}
