package pl.piotrFigura.ToDoApp.reports;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.piotrFigura.ToDoApp.event.TaskDone;
import pl.piotrFigura.ToDoApp.event.TaskUndone;

@Service
@Slf4j
class ChangedTaskEventListener {

    @EventListener
    void on(TaskDone event){
        log.info("Got " + event);
    }

    @EventListener
    void on(TaskUndone event){
        log.info("Got " + event);
    }
}
