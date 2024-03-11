package pl.piotrFigura.ToDoApp.task;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
abstract class BaseAuditableEntity {

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
