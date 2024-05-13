package pl.piotrFigura.ToDoApp.task.interfaces;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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
import org.springframework.web.bind.annotation.RestController;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.domain.contract.GroupReadModel;
import pl.piotrFigura.ToDoApp.task.domain.contract.GroupWriteModel;
import pl.piotrFigura.ToDoApp.task.infrastructure.TaskGroupService;
import pl.piotrFigura.ToDoApp.task.infrastructure.TaskService;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskGroupRepository;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskRepository;

@RestController
@RequestMapping("/groups")
class TaskGroupController {

    private final TaskGroupService service;
    private final TaskRepository taskRepository;

    public TaskGroupController(TaskGroupService service, TaskRepository taskRepository) {
        this.service = service;
        this.taskRepository = taskRepository;
    }

    @PostMapping()
    ResponseEntity<GroupReadModel> createGroup(@Valid @RequestBody GroupWriteModel toCreate) {
        return ResponseEntity.created(URI.create("/")).body(service.crateGroup(toCreate));
    }

    @GetMapping()
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        return ResponseEntity.ok(service.readAll());
    }
    @GetMapping("/{id}")
    ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable Long id){
        return ResponseEntity.ok(taskRepository.findAllByTaskGroups_Id(id));
    }

    @Transactional //na poczatku BEGIN a na koniec COMMIT na bazie
    @PatchMapping("/{id}")
    public ResponseEntity<Task> toggleGroup(@PathVariable Long id) {
        service.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }
}
