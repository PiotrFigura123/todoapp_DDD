package pl.piotrFigura.ToDoApp.task.interfaces;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.domain.TaskDto;
import pl.piotrFigura.ToDoApp.task.infrastructure.TaskFacade;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskRepository;

@RestController
@RequestMapping("/tasks")
class TaskController {

    private final TaskFacade service;


    TaskController(TaskFacade service) {
        this.service = service;
    }

    @GetMapping()
    ResponseEntity<List<TaskDto>> readAllTasks() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<TaskDto> readTask(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findTask(id));
    }
    @GetMapping(value = "/search/done", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<TaskDto>> searchByDone(@RequestParam(defaultValue = "true") Boolean state){
        return ResponseEntity.ok()
            .body(service.searchByDone(state));
    }

    @PostMapping()
    ResponseEntity<TaskDto> createTask(@Valid @RequestBody Task toCreate) {
        TaskDto result = service.createTask(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @Transactional
    @PutMapping("/{id}")
    ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task toUpdate) {
        if (!service.existById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.updateTask(id, toUpdate);
        return ResponseEntity.noContent().build();
    }

    @Transactional //na poczatku BEGIN a na koniec COMMIT na bazie
    @PatchMapping("/{id}")
    ResponseEntity<Task> toggleTask(@PathVariable Long id) {
        if (!service.existById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.toggleTask(id);
        return ResponseEntity.noContent().build();
    }
}
