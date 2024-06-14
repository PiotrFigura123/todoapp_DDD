package pl.piotrFigura.ToDoApp.task;

import pl.piotrFigura.ToDoApp.task.dto.TaskDto;


import java.util.List;

public interface TaskQueryRepository {
    List<TaskDto> findAllBy();

    boolean existsById(Long id);

    List<TaskDto> findDtoByDone(boolean done);

    List<TaskDto> findDtoByGroup_Id(Long groupId);
    boolean existsByDoneIsFalseAndGroup_Id(Long groupId);

}
