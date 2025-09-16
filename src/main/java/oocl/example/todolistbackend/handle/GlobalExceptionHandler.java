package oocl.example.todolistbackend.handle;

import oocl.example.todolistbackend.exception.TodoNoFoundException;
import oocl.example.todolistbackend.exception.TodoTextIllegalException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TodoTextIllegalException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String handleTodoTextIllegalException(Exception e) {
        return e.getMessage();
    }


    @ExceptionHandler(TodoNoFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleTodoNoFoundException(Exception e) {
        return e.getMessage();
    }
}
