package oocl.example.todolistbackend.service;

import lombok.RequiredArgsConstructor;
import oocl.example.todolistbackend.entity.Todo;
import oocl.example.todolistbackend.exception.TodoNoFoundException;
import oocl.example.todolistbackend.exception.TodoTextIllegalException;
import oocl.example.todolistbackend.repository.TodoRepository;
import oocl.example.todolistbackend.req.TodoUpdateReq;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void update(Long id , TodoUpdateReq todoUpdateReq) {
        Todo todo = todoRepository.getById(id);
        if (todo == null){
            throw new TodoNoFoundException("该条todo不存在");
        }
        if (todoUpdateReq.getDone() != null) {
            todo.setDone(todoUpdateReq.getDone());
        }
        if (todoUpdateReq.getText() != null) {
            if (todoUpdateReq.getText().isBlank()) {
                throw new TodoTextIllegalException("text为必填项");
            }
            todo.setText(todoUpdateReq.getText());
        }
        todoRepository.update(todo);
    }

    public void delete(long id) {
        boolean removed = todoRepository.removeById(id);
        if (!removed) {
            throw new TodoNoFoundException("该条todo不存在");
        }
    }
}
