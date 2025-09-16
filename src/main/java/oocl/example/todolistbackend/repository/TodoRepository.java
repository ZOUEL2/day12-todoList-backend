package oocl.example.todolistbackend.repository;

import lombok.RequiredArgsConstructor;
import oocl.example.todolistbackend.entity.Todo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TodoRepository {

    private final TodoJpaRepository todoJpaRepository;

    public List<Todo> listAll() {
        return todoJpaRepository.findAll();
    }

    public void addTodo(Todo todo) {
        todoJpaRepository.save(todo);
    }

    public void clear() {
        todoJpaRepository.deleteAll();
    }

    public Todo getById(Long id) {
        return todoJpaRepository.findById(id).orElse(null);
    }

    public void update(Todo todo) {
        todoJpaRepository.save(todo);
    }

    public boolean removeById(long id) {
        if (todoJpaRepository.existsById(id)) {
            todoJpaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
