package br.com.douglass.finances.controller;

import br.com.douglass.finances.controller.dto.TransactionForm;
import br.com.douglass.finances.entity.Expense;
import br.com.douglass.finances.exceptions.RegistryNotFoundException;
import br.com.douglass.finances.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseRepository expenseRepository;

    @PostMapping
    public ResponseEntity<Expense> create(@Valid @RequestBody TransactionForm form) {
        Expense expense = expenseRepository.save(new Expense(
                form.getDescription(),
                form.getValue(),
                form.getDate()
        ));
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(expense.getId())
                .toUri();
        return ResponseEntity.created(uri).body(expense);
    }

    @GetMapping
    public Page<Expense> findAll(@PageableDefault(sort = "date") Pageable pageable) {
        return expenseRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Expense findById(@PathVariable Long id) {
        return expenseRepository.findById(id).orElseThrow(RegistryNotFoundException::new);
    }

    @PutMapping("/{id}")
    public Expense update(@PathVariable Long id, @Valid @RequestBody TransactionForm form) {
        Expense expense = findById(id);

        expense.setDescription(form.getDescription());
        expense.setDate(form.getDate());
        expense.setValue(form.getValue());

        return expenseRepository.save(expense);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        expenseRepository.delete(findById(id));
    }
}
