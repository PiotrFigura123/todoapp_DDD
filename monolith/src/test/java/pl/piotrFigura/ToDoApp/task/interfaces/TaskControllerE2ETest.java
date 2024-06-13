package pl.piotrFigura.ToDoApp.task.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.piotrFigura.ToDoApp.ToDoAppIT;
import pl.piotrFigura.ToDoApp.task.domain.Task;
import pl.piotrFigura.ToDoApp.task.domain.TaskDto;
import pl.piotrFigura.ToDoApp.task.infrastructure.TaskFacade;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskQueryRepository;
import pl.piotrFigura.ToDoApp.task.infrastructure.jpa.TaskRepository;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ToDoAppIT
@ExtendWith(MockitoExtension.class)
class TaskControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private TaskFacade taskFacade;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskQueryRepository taskQueryRepository;
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
        int dbSize = taskQueryRepository.findAllBy().size();
        taskFacade.save( TaskDto.builder()
                .withDescription("foo")
                .withDone(false)
                .withDeadline(LocalDateTime.now())
                .build());
        taskFacade.save( TaskDto.builder()
                .withDescription("bar")
                .withDone(false)
                .withDeadline(LocalDateTime.now())
                .build());
        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URI_GET_EVENTS));
        //then
        resultActions.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(dbSize+2)));
    }
    @Test
    @DisplayName("Should return status 200 and return first task")
    void taskControllerThirdTest() throws Exception {
        //given
        var taskDto1 = TaskDto.builder()
                .withDescription("foo")
                .withDone(false)
                .withDeadline(LocalDateTime.now())
                .withId(1l)
                .build();
        taskFacade.save( taskDto1);
        taskFacade.save( TaskDto.builder()
                .withDescription("bar")
                .withDone(false)
                .withDeadline(LocalDateTime.now())
                .build());
        Long taskId = 1l;
        //when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.get(ENDPOINT_URI_GET_EVENTS + "/{id}", taskId));
        //then
        resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.description").value("foo"));
    }
    @Test
    @DisplayName("Should return status 400 and return IllegalArgumentException")
    void taskControllerFourthTest() throws Exception {
        //given
        taskFacade.save( TaskDto.builder()
                .withDescription("foo")
                .withDone(false)
                .withDeadline(LocalDateTime.now())
                .build());
        taskFacade.save( TaskDto.builder()
                .withDescription("bar")
                .withDone(false)
                .withDeadline(LocalDateTime.now())
                .build());
        Long taskId = 3l;
        //when + then
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URI_GET_EVENTS + "/{id}", taskId))
            .andExpect(status().is4xxClientError());
    }
    @Test
    @DisplayName("Should return status 200 and add new task to repo")
    void taskControllerFifthTest() throws Exception {
        //given
        objectMapper.findAndRegisterModules();
        taskFacade.save( TaskDto.builder()
                .withDescription("foo")
                .withDone(false)
                .withDeadline(LocalDateTime.now())
                .build());
        taskFacade.save( TaskDto.builder()
                .withDescription("bar")
                .withDone(false)
                .withDeadline(LocalDateTime.now())
                .build());
        Task newTask = new Task("new", LocalDateTime.now(), null);
        int beginSize = taskQueryRepository.findAllBy().size();
        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT_URI_GET_EVENTS)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(newTask)));
        //then
        resultActions.andExpect(status().isCreated());
        assertEquals(beginSize + 1, taskQueryRepository.findAllBy().size());
    }
}