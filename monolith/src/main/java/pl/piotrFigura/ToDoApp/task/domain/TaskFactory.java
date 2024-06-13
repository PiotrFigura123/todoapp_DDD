package pl.piotrFigura.ToDoApp.task.domain;

import org.springframework.stereotype.Service;

@Service
public class TaskFactory {
    Task from (TaskDto source, TaskGroups groups){
        var result =  new Task(source.getDescription(), source.getDeadline(), groups);
        result.setId(source.getId());
        result.setDone(source.isDone());
        return result;
    }
}
