package pl.piotrFigura.ToDoApp.task.interfaces;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.piotrFigura.ToDoApp.ToDoAppIT;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskRepository;

@ToDoAppIT
class TaskControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TaskRepository taskRepository;

    public static final String ENDPOINT_URI_GET_EVENTS = "/tasks";
    private ObjectMapper objectMapper = new ObjectMapper();
    @Test
    @DisplayName("Should return status 400 after receiving request without body and do not return tasks")
    void taskControllerFirstTest() throws Exception {
        //given
        //when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.post(ENDPOINT_URI_GET_EVENTS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().is4xxClientError());
    }
    @Test
    @DisplayName("Should return status 200 and return all tasks")
    void taskControllerSecondTest() throws Exception {
        //given
        taskRepository.save(new Task("foo", LocalDateTime.now()));
        taskRepository.save(new Task("bat", LocalDateTime.now()));
        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URI_GET_EVENTS));
        //then
        resultActions.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }
    @Test
    @DisplayName("Should return status 200 and return first task")
    void taskControllerThirdTest() throws Exception {
        //given
        taskRepository.save(new Task("foo", LocalDateTime.now()));
        taskRepository.save(new Task("bat", LocalDateTime.now()));
        Long taskId = 1l;
        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URI_GET_EVENTS +"/{id}", taskId));
        //then
        resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.description").value("foo"));
    }

    @Test
    @DisplayName("Should return status 400 and return IllegalArgumentException")
    void taskControllerFourthTest() throws Exception {
        //given
        taskRepository.save(new Task("foo", LocalDateTime.now()));
        taskRepository.save(new Task("bat", LocalDateTime.now()));
        Long taskId = 3l;
        //when + then
         mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URI_GET_EVENTS +"/{id}", taskId))
             .andExpect(status().is4xxClientError());
    }
    @Test
    @DisplayName("Should return status 200 and add new task to repo")
    void taskControllerFifthTest() throws Exception {
        //given
        objectMapper.findAndRegisterModules();
        taskRepository.save(new Task("foo", LocalDateTime.now()));
        taskRepository.save(new Task("bat", LocalDateTime.now()));
        Task newTask = new Task("new", LocalDateTime.now());
        int beginSize = taskRepository.findAll().size();
        //when

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT_URI_GET_EVENTS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(newTask)));
        //then
        resultActions.andExpect(status().isCreated());
        assertEquals(beginSize+1, taskRepository.findAll().size());
    }
}