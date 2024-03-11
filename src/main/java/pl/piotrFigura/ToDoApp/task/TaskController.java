package pl.piotrFigura.ToDoApp.task;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
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

@Controller
@RequestMapping("/tasks")
class TaskController {

    private final TaskRepository taskRepository;

    TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping()
    ResponseEntity<List<Task>> readAllTasks() {
        return ResponseEntity.ok().body(taskRepository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Task> readTask(@PathVariable Long id) {
        return ResponseEntity.ok()
            .body(taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("nie istnieje id")));
    }

    @PostMapping()
    ResponseEntity<Task> createTast(@Valid @RequestBody Task toCreate) {
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
    public ResponseEntity<Task> toggleTask(@PathVariable Long id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.findById(id)
                .ifPresent(task -> task.setDone(!task.isDone()));
        return ResponseEntity.noContent().build();
    }
}
