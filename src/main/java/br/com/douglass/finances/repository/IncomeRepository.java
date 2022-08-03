package br.com.douglass.finances.repository;

import br.com.douglass.finances.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {
}
