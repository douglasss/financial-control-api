package br.com.douglass.finances.controller;

import br.com.douglass.finances.controller.dto.TransactionForm;
import br.com.douglass.finances.entity.Income;
import br.com.douglass.finances.exceptions.RegistryNotFoundException;
import br.com.douglass.finances.repository.IncomeRepository;
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
@RequestMapping("/api/incomes")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeRepository incomeRepository;

    @PostMapping
    public ResponseEntity<Income> create(@Valid @RequestBody TransactionForm form) {
        Income income = incomeRepository.save(new Income(
                form.getDescription(),
                form.getValue(),
                form.getDate()
        ));
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(income.getId())
                .toUri();
        return ResponseEntity.created(uri).body(income);
    }

    @GetMapping
    public Page<Income> findAll(@PageableDefault(sort = "date") Pageable pageable) {
        return incomeRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Income findById(@PathVariable Long id) {
        return incomeRepository.findById(id).orElseThrow(RegistryNotFoundException::new);
    }

    @PutMapping("/{id}")
    public Income update(@PathVariable Long id, @Valid @RequestBody TransactionForm form) {
        Income income = findById(id);

        income.setDescription(form.getDescription());
        income.setDate(form.getDate());
        income.setValue(form.getValue());

        return incomeRepository.save(income);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        incomeRepository.delete(findById(id));
    }
}
