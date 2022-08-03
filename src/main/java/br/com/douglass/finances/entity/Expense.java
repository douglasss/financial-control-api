
package br.com.douglass.finances.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
@Getter
@Setter
public class Expense extends Transaction {
    public Expense(String description, BigDecimal value, LocalDate date) {
        super(description, value, date);
    }

    public Expense() {
        super();
    }
}
