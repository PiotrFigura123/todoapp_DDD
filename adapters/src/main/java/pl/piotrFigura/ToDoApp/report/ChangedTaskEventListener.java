package pl.piotrFigura.ToDoApp.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
