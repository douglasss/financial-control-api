package br.com.douglass.finances.repository;

import br.com.douglass.finances.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
