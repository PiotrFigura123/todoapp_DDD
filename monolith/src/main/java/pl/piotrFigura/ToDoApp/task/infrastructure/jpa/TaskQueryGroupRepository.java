package pl.piotrFigura.ToDoApp.task.infrastructure.jpa;

import pl.piotrFigura.ToDoApp.task.domain.TaskGroups;

import java.util.List;

public interface TaskQueryGroupRepository {
    List<TaskGroups> findAll();
    boolean existsByDoneIsFalseAndProject_Id(Long id);

    boolean existsByDescription(String description);
}
