package br.com.concrete.todolist.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigInteger;
import java.time.LocalDate;

@Tag(name = "todos", description = "Registration Todo")
@Data
public class TodoDTO {


    private BigInteger id;

    @NotBlank(message = "The todo title can not is empty")
    @Schema(description = "Title of the Todo", example = "Esporte", required = true)
    private String title;

    @NotBlank(message = "The todo description can not is empty")
    private String description;


    @PastOrPresent(message = "The date must be on or before today's date")
    @NotNull(message = "Date cannot be null or empty")
    @JsonProperty(value = "start_date")
    private LocalDate startDate;

    @FutureOrPresent(message = "The date must be on or after today's date")
    @NotNull(message = "Date cannot be null or empty")
    @JsonProperty(value = "end_date")
    private LocalDate endDate;


}
