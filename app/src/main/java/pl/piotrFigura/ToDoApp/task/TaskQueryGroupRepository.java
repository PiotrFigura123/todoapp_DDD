package pl.piotrFigura.ToDoApp.task;

import java.util.List;

public interface TaskQueryGroupRepository {
    List<TaskGroups> findAll();
    boolean existsByDoneIsFalseAndProject_Id(Long id);

    boolean existsByDescription(String description);
}
