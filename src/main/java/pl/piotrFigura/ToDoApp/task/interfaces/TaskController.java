package pl.piotrFigura.ToDoApp.task.interfaces;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.infrastructure.TaskService;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskRepository;

@Controller
@RequestMapping("/tasks")
class TaskController {

    private final TaskRepository taskRepository;
    private final TaskService service;

    private final ApplicationEventPublisher publisher;
    TaskController(TaskRepository taskRepository, TaskService service, ApplicationEventPublisher publisher) {
        this.taskRepository = taskRepository;
        this.service = service;
        this.publisher = publisher;
    }

    @GetMapping()
    ResponseEntity<List<Task>> readAllTasks() {
        return ResponseEntity.ok().body(taskRepository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Task> readTask(@PathVariable Long id) {
        return taskRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping(value = "/search/done", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Task>> searchByDone(@RequestParam(defaultValue = "true") Boolean state){
        return ResponseEntity.ok()
            .body(taskRepository.findByDone(state));
    }

    @PostMapping()
    ResponseEntity<Task> createTask(@Valid @RequestBody Task toCreate) {
        Task result = taskRepository.save(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @Transactional
    @PutMapping("/{id}")
    ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task toUpdate) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
            taskRepository.findById(id)
                .ifPresent(task -> {
                    task.updateFrom(toUpdate);
                    taskRepository.save(task);
                    });
        return ResponseEntity.noContent().build();
    }

    @Transactional //na poczatku BEGIN a na koniec COMMIT na bazie
    @PatchMapping("/{id}")
    ResponseEntity<Task> toggleTask(@PathVariable Long id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.findById(id)
            .map(Task::toggle)
                .ifPresent(publisher::publishEvent);
        return ResponseEntity.noContent().build();
    }
}
