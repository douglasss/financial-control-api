package br.com.douglass.finances.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Transaction extends BaseEntity {

    @Column(length = 100, nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal value;

    @Column(nullable = false)
    private LocalDate date;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    @Setter(AccessLevel.PROTECTED)
    @JsonIgnore
    public Month month;

    public Transaction(String description, BigDecimal value, LocalDate date) {
        setDescription(description);
        setValue(value);
        setDate(date);
    }

    public void setDate(LocalDate date) {
        this.date = date;
        setMonth(Optional.ofNullable(date)
                .map(LocalDate::getMonth)
                .orElse(null));
    }
}
