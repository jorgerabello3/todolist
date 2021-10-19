package br.com.concrete.todolist.exception.handler;

import br.com.concrete.todolist.errors.exception.TodoBadRequestException;
import br.com.concrete.todolist.errors.exception.TodoExceptionDetails;
import br.com.concrete.todolist.errors.exception.TodoNotFoundException;
import br.com.concrete.todolist.errors.exception.TodoValidationExceptionDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class TodoExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<TodoExceptionDetails> handleTodoNotFoundException(TodoNotFoundException todoNotFoundException) {
        return new ResponseEntity<>(
                TodoExceptionDetails.builder()
                        .title("Bad Request Excepton, Check the Documentation")
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .details(todoNotFoundException.getMessage())
                        .developerMessage(todoNotFoundException.getClass().getName())
                        .build(), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));

        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));


        return new ResponseEntity<>(TodoValidationExceptionDetails.builder().title("Bad Request Excepton, Check the Documentation")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Bad Request Exception, Invalid fields")
                .details("Check the fields(s) error")
                .developerMessage(exception.getClass().getName())
                .fields(fields)
                .fieldsMessage(fieldsMessage)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TodoBadRequestException.class)
    public ResponseEntity<TodoExceptionDetails> handleTodoBadRequestException(TodoBadRequestException todoBadRequestException) {
        return new ResponseEntity<>(
                TodoExceptionDetails.builder()
                        .title("Bad Request Excepton, Check the Documentation")
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .details(todoBadRequestException.getMessage())
                        .developerMessage(todoBadRequestException.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST);
    }

}
