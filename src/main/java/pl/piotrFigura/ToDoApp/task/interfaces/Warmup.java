package pl.piotrFigura.ToDoApp.task.interfaces;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.domain.TaskDto;
import pl.piotrFigura.ToDoApp.task.domain.TaskGroups;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskGroupRepository;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskQueryGroupRepository;

@Component("warmupTaskGroup")
@Slf4j
class Warmup implements ApplicationListener<ContextRefreshedEvent> {

    private final TaskGroupRepository groupRepository;
    private final TaskQueryGroupRepository taskQueryGroupRepository;

    Warmup(TaskGroupRepository groupRepository, final TaskQueryGroupRepository taskQueryGroupRepository) {
        this.groupRepository = groupRepository;
        this.taskQueryGroupRepository = taskQueryGroupRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
      log.info("Application warmup after context refreshed");
        final String description = "ApplicationContextEvent";
        if(!taskQueryGroupRepository.existsByDescription(description)){
            log.info("No required group found! Adding it!");
            var group = new TaskGroups();
            group.setDescription(description);
            group.setTasks(Set.of(

                new Task("ContextClosedEvent", null, group),
                new Task("ContextRefreshedEvent", null, group),
                new Task("ContextStoppedEvent", null, group),
                new Task("ContextStartedEvent", null, group)
            ));
        groupRepository.save(group);
        }
    }
}
