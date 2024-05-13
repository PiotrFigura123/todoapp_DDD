package pl.piotrFigura.ToDoApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskRepository;

@Configuration
class TestConfiguration {

    @Bean
    @Profile("integration")
    TaskRepository testRepo() {
        return new TaskRepository() {

            private Map<Long, Task> tasks = new HashMap<>();

            @Override
            public Optional<Task> findById(Long id) {
                return Optional.ofNullable(tasks.get(id));
            }

            @Override
            public Task save(Task source) {
                return tasks.put(tasks.size() + 1l, source);
            }

            @Override
            public List<Task> findAll() {
                return new ArrayList<>(tasks.values());
            }

            @Override
            public boolean existsById(Long id) {
                return tasks.containsKey(id);
            }

            @Override
            public boolean existsByTaskGroup(boolean isDone, Long groupId) {
                return false;
            }

            @Override
            public List<Task> findByDone(boolean done) {
                return new ArrayList<>(tasks.values());
            }

            @Override
            public List<Task> findAllByTaskGroups_Id(Long groupId) {
                return List.of();
            }

        };
    }
}
