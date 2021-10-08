package br.com.concrete.todolist.errors.exception;


public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(String message) {
        super(message);
    }
}
