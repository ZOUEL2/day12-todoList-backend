package oocl.example.todolistbackend.exception;

public class TodoNoFoundException extends RuntimeException {
    public TodoNoFoundException(String message) {
        super(message);
    }
}
