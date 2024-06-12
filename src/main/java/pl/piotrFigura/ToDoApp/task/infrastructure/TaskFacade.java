package pl.piotrFigura.ToDoApp.task.infrastructure;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.domain.TaskFactory;
import pl.piotrFigura.ToDoApp.task.domain.TaskDto;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskQueryRepository;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskRepository;
@Service
@Slf4j
@RequiredArgsConstructor
public class TaskFacade {

    private final TaskRepository taskRepository;
    private final TaskQueryRepository taskQueryRepository;
    private final TaskFactory factory;
    private final ApplicationEventPublisher publisher;

    public List<TaskDto> readAll(){
        return  taskQueryRepository.findAll().stream().map(task -> task.toDto()).toList();
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
           Task task = taskRepository.findById(toSave.getId())
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
                    });
        return taskRepository.save(task).toDto();
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
        return taskQueryRepository.existsByDoneIsFalseAndGroup_Id( groupId);
    }

    public List<TaskDto> findAllTasksWithGroupId(Long id) {
        return taskQueryRepository.findAllByGroup_Id(id)
                .stream()
                .map(task ->
                        task.toDto())
                .toList();
    }

    public List<TaskDto> findAll() {
    return taskQueryRepository.findAll().stream().map(Task::toDto).toList();
    }

    public TaskDto findTask( Long id) {
        return taskRepository.findById(id).map(Task::toDto).orElseThrow(()-> new IllegalArgumentException("doesn't exist"));
    }

    public List<TaskDto> searchByDone(Boolean state) {
        return taskQueryRepository.findByDone(state).stream().map(Task::toDto).toList();
    }

    public TaskDto createTask(Task toCreate) {
        return taskRepository.save(toCreate).toDto();
    }

    public void updateTask(Long id, Task toUpdate) {
        taskRepository.findById(id)
                .ifPresent(task -> {
                    task.updateFrom(toUpdate);
                    taskRepository.save(task);
                });
    }

    public boolean existById(Long id) {
        return taskQueryRepository.existsById(id);
    }

    public void toggleTask(final Long id) {
        taskRepository.findById(id)
                .map(Task::toggle)
                .ifPresent(publisher::publishEvent);
    }
}
