package pl.piotrFigura.ToDoApp.report;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piotrFigura.ToDoApp.task.dto.TaskDto;
import pl.piotrFigura.ToDoApp.task.TaskFacade;

@RestController
@RequestMapping("/reports")
class ReportController {

    private final TaskFacade taskFacade;
    private final PersistedTaskEventRepository eventRepository;

    ReportController( final TaskFacade taskFacade, PersistedTaskEventRepository eventRepository) {
        this.taskFacade = taskFacade;
        this.eventRepository = eventRepository;
    }

    @GetMapping("/count/{id}")
    ResponseEntity<TaskWithChangesCount> readTaskWithCount(@PathVariable long id){
        return taskFacade.readTaskWithCount(id).map(task -> new TaskWithChangesCount(task, eventRepository.findByTaskId(id)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    private static class TaskWithChangesCount {

        public String description;
        public boolean done;
        public int changesCount;
        TaskWithChangesCount(TaskDto task, List<PersistedTaskEvent> events) {
        description = task.getDescription();
        done = task.isDone();
        changesCount = events.size();
        }
    }
}
