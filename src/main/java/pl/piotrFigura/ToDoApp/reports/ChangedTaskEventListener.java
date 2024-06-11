package pl.piotrFigura.ToDoApp.reports;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.piotrFigura.ToDoApp.reports.event.TaskDone;
import pl.piotrFigura.ToDoApp.reports.event.TaskEvent;
import pl.piotrFigura.ToDoApp.reports.event.TaskUndone;

@Service
@Slf4j
class ChangedTaskEventListener {

    private final PersistedTaskEventRepository repository;

    ChangedTaskEventListener(PersistedTaskEventRepository repository) {
        this.repository = repository;
    }

    @Async
    @EventListener
    void on(TaskDone event){
        log.info("Got " + event);
        onChanged(event);
    }

    @Async
    @EventListener
    void on(TaskUndone event){
        log.info("Got " + event);
        onChanged(event);
    }
    private void onChanged(TaskEvent event){
        log.info("Got " + event.getClass().getSimpleName());
        repository.save(new PersistedTaskEvent(event));
    }
}
