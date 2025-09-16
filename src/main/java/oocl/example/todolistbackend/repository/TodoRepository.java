package oocl.example.todolistbackend.repository;

import lombok.RequiredArgsConstructor;
import oocl.example.todolistbackend.entity.Todo;
import oocl.example.todolistbackend.req.TodoReq;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TodoRepository {

    private final TodoJpaRepository todoJpaRepository;

    public List<Todo> listAll(){
        return todoJpaRepository.findAll();
    }

    public void addTodo(Todo todo){
        todoJpaRepository.save(todo);
    }

    public void clear() {
        todoJpaRepository.deleteAll();
    }
}
