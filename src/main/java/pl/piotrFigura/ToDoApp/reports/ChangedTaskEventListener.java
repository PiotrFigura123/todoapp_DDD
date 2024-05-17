package pl.piotrFigura.ToDoApp.reports;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.piotrFigura.ToDoApp.event.TaskDone;
import pl.piotrFigura.ToDoApp.event.TaskEvent;
import pl.piotrFigura.ToDoApp.event.TaskUndone;

@Service
@Slf4j
class ChangedTaskEventListener {

    private final PersistedTaskEventRepository repository;

    ChangedTaskEventListener(PersistedTaskEventRepository repository) {
        this.repository = repository;
    }

    @EventListener
    void on(TaskDone event){
        log.info("Got " + event);
        onChanged(event);
    }


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
