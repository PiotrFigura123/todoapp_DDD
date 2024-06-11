package pl.piotrFigura.ToDoApp.task.infrastructure;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.domain.TaskFactory;
import pl.piotrFigura.ToDoApp.task.domain.TaskDto;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskRepository;
@Service
@Slf4j
@RequiredArgsConstructor
public class TaskFacade {

    private final TaskRepository taskRepository;
    private final TaskFactory factory;

    public List<TaskDto> readAll(){
        return  taskRepository.findAll().stream().map(task -> task.toDto()).toList();
    }
    public TaskDto get(Long id){
        Task task = taskRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("don't exist"));
        return TaskDto.builder().withId(task.getId())
                .withDeadline(task.getDeadline())
                .withDescription(task.getDescription())
                .withDone(task.isDone())
                .build();


    }
    public void delete(Long id){
        taskRepository.deleteById(id);
    }

    public TaskDto save(TaskDto toSave){
        return taskRepository.save(
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
            ).toDto();
    }

    public Optional<TaskDto> readTaskWithCount(long id) {
        return taskRepository.findById(id)
                .map(task ->
                        TaskDto.builder()
                                .withId(task.getId())
                                .withDescription(task.getDescription())
                                .withDone(task.isDone())
                                .withDeadline(task.getDeadline())
                                .build());
    }

    public boolean areUndoneTasksWithGroup(Long groupId) {
        return taskRepository.existsByDoneIsFalseAndGroup_Id( groupId);
    }

    public List<TaskDto> findAllTasksWithGroupId(Long id) {
        return taskRepository.findAllByGroup_Id(id)
                .stream()
                .map(task ->
                        task.toDto())
                .toList();
    }
}
