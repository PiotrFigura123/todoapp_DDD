package pl.piotrFigura.ToDoApp.reports;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
interface PersistedTaskEventRepository extends JpaRepository<PersistedTaskEvent, Long> {
    List<PersistedTaskEvent> findByTaskId(long taskId);

}
