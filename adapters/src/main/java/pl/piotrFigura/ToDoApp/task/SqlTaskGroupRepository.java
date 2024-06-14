package pl.piotrFigura.ToDoApp.task;

import org.springframework.data.repository.Repository;

interface SqlTaskGroupRepository extends TaskGroupRepository, Repository<TaskGroups, Long> {
}
