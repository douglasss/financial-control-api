package br.com.douglass.finances.controller.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionForm {
    @NotEmpty
    @Size(max = 100)
    private String description;
    @NotNull
    private LocalDate date;
    @NotNull
    @DecimalMin("0.01")
    @DecimalMax("99999999.99")
    private BigDecimal value;
}
