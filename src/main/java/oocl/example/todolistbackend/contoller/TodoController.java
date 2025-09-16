package oocl.example.todolistbackend.contoller;

import lombok.RequiredArgsConstructor;
import oocl.example.todolistbackend.entity.Todo;
import oocl.example.todolistbackend.req.TodoReq;
import oocl.example.todolistbackend.service.TodoService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Map<String, Object> createTodo(@RequestBody TodoReq todoReq) {
        Todo todo = new Todo();
        BeanUtils.copyProperties(todoReq, todo);
        todoService.addTodo(todo);
        return Map.of("id", todo.getId(), "text", todo.getText());
    }

}
