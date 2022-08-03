package br.com.douglass.finances.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "fieldErrors")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestErrorMessageDTO {
    private final int status;
    private final Date timestamp = new Date();
    private final String message;
    private final String path;
    private Set<RestFieldErrorDTO> fieldErrors;

    public RestErrorMessageDTO(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }

    public void addFieldError(RestFieldErrorDTO restFieldError) {
        if (fieldErrors == null) {
            fieldErrors = new HashSet<>();
        }
        fieldErrors.add(restFieldError);
    }
}