package pl.piotrFigura.ToDoApp.task.infrastructure;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.domain.contract.TaskDto;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskRepository;
@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> readAll(){
        return  taskRepository.findAll();
    }
    public Optional<TaskDto> get(Long id){
        return taskRepository.findById(id).map(TaskDto::new);
    }
    public void delete(Long id){
        taskRepository.deleteById(id);
    }

    public TaskDto save(TaskDto toSave){
        return new TaskDto(
            taskRepository.save(
                taskRepository.findById(toSave.getId())
                    .map(existingTask -> {
                        if ( existingTask.isDone() != toSave.isDone()){
                            existingTask.setDone(toSave.isDone());
                        }
                        existingTask.setDeadline(toSave.getDeadline());
                        existingTask.setDescription(toSave.getDescription());
                        return existingTask;
                    }).orElseGet(()-> {
                        var result = new Task(toSave.getDescription(), toSave.getDeadline(), null);
                        return result;
                    })
            )
        );
    }
}
