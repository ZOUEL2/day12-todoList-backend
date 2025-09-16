package oocl.example.todolistbackend.service;

import lombok.RequiredArgsConstructor;
import oocl.example.todolistbackend.entity.Todo;
import oocl.example.todolistbackend.exception.TodoTextIllegalException;
import oocl.example.todolistbackend.repository.TodoRepository;
import oocl.example.todolistbackend.req.TodoReq;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<Todo> getAllTodos(){
        return todoRepository.listAll();
    }

    public void addTodo(Todo todo) {
        if (todo.getText() == null || todo.getText().isBlank()){
            throw new TodoTextIllegalException("text为必填项");
        }
        todoRepository.addTodo(todo);
    }
}
