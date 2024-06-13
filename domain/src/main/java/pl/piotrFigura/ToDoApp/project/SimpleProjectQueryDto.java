package pl.piotrFigura.ToDoApp.project;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "projects")
public class SimpleProjectQueryDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    public SimpleProjectQueryDto(final Long id, final String description) {
        this.id = id;
        this.description = description;
    }

    protected SimpleProjectQueryDto() {

    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
