package pl.piotrFigura.ToDoApp.task.domain.contract;

import pl.piotrFigura.ToDoApp.task.domain.TaskDto;

class GroupTaskReadModel {

    private String description;
    private boolean done;

    public GroupTaskReadModel(TaskDto source) {
        description = source.getDescription();
        done = source.isDone();
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    void setDone(boolean done) {
        this.done = done;
    }
}
