package pl.piotrFigura.ToDoApp.task;

import org.springframework.data.repository.Repository;

interface SqlTaskRepository extends TaskRepository, Repository<Task, Long> {
}
