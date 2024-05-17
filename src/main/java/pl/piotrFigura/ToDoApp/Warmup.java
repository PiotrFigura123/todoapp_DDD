package pl.piotrFigura.ToDoApp;

import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.domain.TaskGroups;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskGroupRepository;

@Component
@Slf4j
class Warmup implements ApplicationListener<ContextRefreshedEvent> {

    private final TaskGroupRepository groupRepository;

    Warmup(TaskGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
      log.info("Application warmup after context refreshed");
        final String description = "ApplicationContextEvent";
        if(!groupRepository.existsByDescription(description)){
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
