package pl.piotrFigura.ToDoApp.task.infrastructure.jpa;

import java.util.Optional;
import pl.piotrFigura.ToDoApp.task.domain.TaskGroups;

public interface TaskGroupRepository {

    Optional<TaskGroups> findById(Long id);

    TaskGroups save(TaskGroups entity);

}
