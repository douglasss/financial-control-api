package br.com.douglass.finances.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "incomes")
@Getter
@Setter
public class Income extends Transaction {
    public Income(String description, BigDecimal value, LocalDate date) {
        super(description, value, date);
    }

    public Income() {
        super();
    }
}
