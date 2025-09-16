package oocl.example.todolistbackend.repository;

import oocl.example.todolistbackend.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TodoJpaRepository extends JpaRepository<Todo, Long> {

}
