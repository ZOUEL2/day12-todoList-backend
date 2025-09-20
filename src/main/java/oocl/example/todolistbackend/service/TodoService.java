package oocl.example.todolistbackend.service;

import lombok.RequiredArgsConstructor;
import oocl.example.todolistbackend.entity.Todo;
import oocl.example.todolistbackend.exception.TodoNoFoundException;
import oocl.example.todolistbackend.exception.TodoTextIllegalException;
import oocl.example.todolistbackend.repository.TodoRepository;
import oocl.example.todolistbackend.req.TodoUpdateReq;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    @Tool(description = "list all todos")
    public List<Todo> getAllTodos() {
        return todoRepository.listAll();
    }

    @Tool(description = "add a todo")
    public void addTodo(Todo todo) {
        if (todo.getText().isBlank()) {
            throw new TodoTextIllegalException("text为必填项");
        }
        todoRepository.addTodo(todo);
    }

    @Tool(description = "update a todo by id")
    public void updateTodo(Long id, TodoUpdateReq todoUpdateReq) {
        Todo todo = todoRepository.getById(id);
        if (todo == null) {
            throw new TodoNoFoundException("该条todo不存在");
        }

        if (todoUpdateReq.getText().isEmpty() || todoUpdateReq.getText().isBlank()) {
            throw new TodoTextIllegalException("text为必填项");
        }

        todo.setDone(todoUpdateReq.getDone());
        todo.setText(todoUpdateReq.getText());
        todoRepository.update(todo);
    }

    @Tool(description = "delete a todo by id")
    public void delete(long id) {
        boolean removed = todoRepository.removeById(id);
        if (!removed) {
            throw new TodoNoFoundException("该条todo不存在");
        }
    }
}
