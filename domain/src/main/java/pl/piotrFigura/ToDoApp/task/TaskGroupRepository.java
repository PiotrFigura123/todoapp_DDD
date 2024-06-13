package pl.piotrFigura.ToDoApp.task;

import java.util.Optional;

interface TaskGroupRepository {

    Optional<TaskGroups> findById(Long id);

    TaskGroups save(TaskGroups entity);

}
