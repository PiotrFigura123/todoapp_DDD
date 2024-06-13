package pl.piotrFigura.ToDoApp.task;

import org.springframework.web.bind.annotation.RequestParam;
import pl.piotrFigura.ToDoApp.task.dto.TaskDto;


import java.util.List;

public interface TaskQueryRepository {
    List<TaskDto> findAllBy();

    boolean existsById(Long id);

    List<TaskDto> findDtoByDone(@RequestParam boolean done);

    List<TaskDto> findDtoByGroup_Id(Long groupId);
    boolean existsByDoneIsFalseAndGroup_Id(Long groupId);

}
