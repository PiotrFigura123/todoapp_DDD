package pl.piotrFigura.ToDoApp.task.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.piotrFigura.ToDoApp.task.domain.Task;

@Repository
interface TaskRepositoryImpl extends TaskRepository, JpaRepository<Task, Long> {
}
