package pl.piotrFigura.ToDoApp.task.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import pl.piotrFigura.ToDoApp.task.domain.TaskDto;
import pl.piotrFigura.ToDoApp.task.domain.TaskFactory;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskRepository;

import java.util.Optional;
@Service
@Slf4j
@RequiredArgsConstructor
public class TaskFacade {

    private final TaskRepository taskRepository;
    private final TaskFactory factory;
    private final ApplicationEventPublisher publisher;

    public TaskDto get(Long id){
        return taskRepository.findDtoById(id).orElseThrow(()-> new IllegalArgumentException("don't exist"));
    }
    public void delete(Long id){
        taskRepository.deleteById(id);
    }

    public TaskDto save(TaskDto toSave){
           TaskDto task = taskRepository.findDtoById(toSave.getId())
                    .map(result -> {
                        result = TaskDto.builder()
                                .withDescription(toSave.getDescription())
                                .withDone(toSave.isDone())
                                .withDeadline(toSave.getDeadline())
                                .build();
                        return result;
                    }).orElseGet(()-> {
                        var result = TaskDto.builder()
                                .withDescription(toSave.getDescription())
                                .withDone(toSave.isDone())
                                .withDeadline(toSave.getDeadline())
                                .build();
                        return result;
                    });
        return taskRepository.save(task);
    }

    public Optional<TaskDto> readTaskWithCount(long id) {
        return taskRepository.findDtoById(id)
                .map(task ->
                        TaskDto.builder()
                                .withId(task.getId())
                                .withDescription(task.getDescription())
                                .withDone(task.isDone())
                                .withDeadline(task.getDeadline())
                                .build());
    }


    public TaskDto findTask( Long id) {
        return taskRepository.findDtoById(id)
                .orElseThrow(()-> new IllegalArgumentException("doesn't exist"));
    }

    public TaskDto createTask(TaskDto toCreate) {
        return taskRepository.save(toCreate);
    }

    public void updateTask(Long id, TaskDto toUpdate) {
        taskRepository.findDtoById(id)
                .ifPresent(task -> {
                    task = TaskDto.builder()
                            .withDeadline(toUpdate.getDeadline())
                            .withDescription(toUpdate.getDescription())
                            .build();
                    taskRepository.save(task);
                });
    }

    public void toggleTask(final Long id) {
        taskRepository.findDtoById(id)
                .map(TaskDto::toggle)
                .ifPresent(publisher::publishEvent);
    }
}
