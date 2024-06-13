package pl.piotrFigura.ToDoApp.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TaskGroupRepositoryImpl extends TaskGroupRepository, JpaRepository<TaskGroups, Long> {
}
