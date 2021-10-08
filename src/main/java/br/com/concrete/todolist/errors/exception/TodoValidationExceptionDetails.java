package br.com.concrete.todolist.errors.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class TodoValidationExceptionDetails extends TodoExceptionDetails{

    private final String fields;
    private final String fieldsMessage;
}
