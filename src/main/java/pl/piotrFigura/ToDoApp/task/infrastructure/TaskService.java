package pl.piotrFigura.ToDoApp.task.infrastructure;

import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskRepository;
import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    @Async
    public CompletableFuture<List<Task>> findAllAsync(){
        log.info("logger from assync");
        return CompletableFuture.supplyAsync(()-> taskRepository.findAll());
    }
}
