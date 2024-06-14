package pl.piotrFigura.ToDoApp.task;

import pl.piotrFigura.ToDoApp.task.dto.TaskDto;

class TaskFactory {
    Task from (TaskDto source, TaskGroups groups){
        var result =  new Task(source.getDescription(), source.getDeadline(), groups);
        result.setId(source.getId());
        result.setDone(source.isDone());
        TaskDto taskDto = TaskDto.builder()
                .withDescription(source.getDescription())
                .withDeadline(source.getDeadline())
                .build();

        return result;
    }
}
