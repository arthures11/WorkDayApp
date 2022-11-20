package bryja.com.WorkDayApp;

import bryja.com.WorkDayApp.Controllers.WorkDayController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WorkDayControllerTests {
    @Autowired
    private MockMvc mvc;
    @Autowired
    WorkDayController controller;


    @Test
    public void contextLoads() {
        Assertions.assertThat(controller).isNotNull();
    }
    @Test
    public void GetWorkdays() throws Exception {
        this.mvc
                .perform(MockMvcRequestBuilders.get("/workdays"))
                //.andExpect(status().is2xxSuccessful());
                .andExpect(status().is4xxClientError());
    }
    @Test
    public void GetEntriesFromWorkDay() throws Exception {
        this.mvc
                .perform(MockMvcRequestBuilders.get("/workdays/1/entries"))
                //.andExpect(status().is2xxSuccessful());
                .andExpect(status().is4xxClientError());
    }
    @Test
    public void UpdateWorkDay() throws Exception {
        this.mvc.perform(put("/workdays/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"date\": \"2022-08-04\"}"))
                .andDo(print())
                //.andExpect(status().is4xxClientError());
                .andExpect(status().isForbidden());

    }
    @Test
    public void UpdateWorkDayWithWrongDateFormat() throws Exception {
        this.mvc.perform(put("/workdays/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"date\": \"2022-99-99\"}"))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }
    @Test
    public void AddWorkDay() throws Exception {
        this.mvc.perform(post("/workdays")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"date\": \"2022-02-02\"}"))
                .andDo(print())
                //.andExpect(status().is4xxClientError());
                .andExpect(status().isForbidden());

    }
    @Test
    public void AddTimeEntry() throws Exception {
        this.mvc.perform(post("/workdays/1/entries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"Opis\", \"time_spent\": 5555}"))
                .andDo(print())
                //.andExpect(status().is4xxClientError());
                .andExpect(status().isForbidden());

    }
    @Test
    public void AddTimeEntryToWorkDayThatDoesNotExists() throws Exception {
        this.mvc.perform(post("/workdays/55/entries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"Opis\", \"time_spent\": 5555}"))
                .andDo(print())
                //.andExpect(status().is4xxClientError());
                .andExpect(status().isForbidden());

    }
    @Test
    public void AddWorkDayWithWrongDateFormat() throws Exception {
        this.mvc.perform(post("/workdays")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"date\": \"2022-99-99\"}"))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }
    @Test
    public void DeleteWorkDay() throws Exception {
                mvc.perform(MockMvcRequestBuilders.delete("/workdays/1"))
                        //.andExpect(status().is4xxClientError());
                        .andExpect(status().isForbidden());
                mvc.perform(MockMvcRequestBuilders.get("/workdays/1"))
                        //.andExpect(status().is4xxClientError());
                        .andExpect(status().is4xxClientError());

    }
    @Test
    public void WorkDayDoesNotExist() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/workdays/5"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }
    @Test
    public void DeleteWorkDayThatDoesNotExist() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/workdays/5"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }



}

