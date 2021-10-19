package br.com.concrete.todolist.errors.exception;

public class TodoBadRequestException extends RuntimeException {
    public TodoBadRequestException(String message) {
        super(message);
    }
}
