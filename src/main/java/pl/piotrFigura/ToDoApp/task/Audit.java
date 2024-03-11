package pl.piotrFigura.ToDoApp.task;

import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

@Embeddable
class Audit {

    private LocalDateTime updatedOn;
    private LocalDateTime createdOn;


    @PrePersist
    private void setCreatedOnTime(){
        createdOn = LocalDateTime.now();
    }
    @PreUpdate
    private void setUpdatedOnTime(){
        updatedOn = LocalDateTime.now();
    }
}
