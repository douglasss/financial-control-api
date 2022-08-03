package br.com.douglass.finances.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestFieldErrorDTO {
    private String field;
    private String message;
}