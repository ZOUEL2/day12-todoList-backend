package oocl.example.todolistbackend;

import jakarta.annotation.Resource;
import oocl.example.todolistbackend.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTests {

    @Resource
    private MockMvc mockMvc;

    @Resource
    private TodoRepository todoRepository;

    @BeforeEach
    public void setup() {
        todoRepository.clear();
    }

    private Long createNewTodoItem(String text) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "text":"%s"
                                }
                                """.formatted(text)))
                .andExpect(status().isCreated())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        return Long.parseLong(content.replaceAll("\\D+", ""));
    }

    @Test
    public void should_list_all_todo() throws Exception {
        createNewTodoItem("1111");
        createNewTodoItem("2222");


        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void should_create_new_todo_item_when_given_legal_todo_text() throws Exception {
        Long id1 = createNewTodoItem("1111");
        Long id2 = createNewTodoItem("2222");

        assertNotNull(id1);
        assertNotNull(id2);
        assert id1 < id2;
    }

    @Test
    public void should_throw_exception_when_given_illegal_todo_text_to_create() throws Exception {
        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "text":""
                                }
                                """))
                .andExpect(status().isUnprocessableEntity());

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "abac":""
                                }
                                """))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_update_todo_when_given_legal_todo_reqBody() throws Exception {
        long oldId = createNewTodoItem("OldText");

        mockMvc.perform(put("/todos/{id}",oldId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "text":"NewName",
                            "done": true
                          }
                        """)
        ).andExpect(status().isOk());

        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(oldId))
                .andExpect(jsonPath("$[0].done").value(true))
                .andExpect(jsonPath("$[0].text").value("NewName"));
    }

    @Test
    void should_return_404_when_put_todo_given_not_exist_id() throws Exception {
        mockMvc.perform(put("/todos/{id}", 999) .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "text":"NewName",
                            "done": true
                          }
                        """)
                ).andExpect(status().isNotFound());
    }


    @Test
    void should_patch_done_status_when_patch_todo_given_legal_done_status() throws Exception {
        long oldId = createNewTodoItem("OldText");

        mockMvc.perform(patch("/todos/{id}",oldId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "done": true
                                  }
                                """)
                ).andExpect(status().isOk());

        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(oldId))
                .andExpect(jsonPath("$[0].done").value(true))
                .andExpect(jsonPath("$[0].text").value("OldText"));
    }

    @Test
    void should_delete_todo_when_delete_todo_given_exist_id() throws Exception {
        long oldId = createNewTodoItem("OldText");

        mockMvc.perform(delete("/todos/{id}",oldId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void should_return_404_when_delete_todo_given_not_exist_id() throws Exception {
        mockMvc.perform(delete("/todos/{id}",999))
                .andExpect(status().isNotFound());
    }


}
