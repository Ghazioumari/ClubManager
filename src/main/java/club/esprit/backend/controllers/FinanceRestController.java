package club.esprit.backend.controllers;

import club.esprit.backend.entities.Finance;
import club.esprit.backend.services.IFinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finances")
public class FinanceRestController {

    @Autowired
    private IFinanceService financeService;

    @PostMapping("/add")
    public ResponseEntity<Finance> addFinance(@RequestBody Finance finance) {
        return ResponseEntity.ok(financeService.addFinance(finance));
    }

    @PutMapping("/update")
    public ResponseEntity<Finance> updateFinance(@RequestBody Finance finance) {
        return ResponseEntity.ok(financeService.updateFinance(finance));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteFinance(@PathVariable Long id) {
        financeService.deleteFinance(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Finance> getFinance(@PathVariable Long id) {
        return ResponseEntity.ok(financeService.getFinance(id));
    }

    @GetMapping("all")
    public ResponseEntity<List<Finance>> getAllFinances() {
        return ResponseEntity.ok(financeService.getAllFinances());
    }
    @GetMapping("getbyclub/{id}")
    public ResponseEntity<List<Finance>> getFinanceByClub(@PathVariable Long id) {
        return ResponseEntity.ok(financeService.getFinanceByClub(id));
    }
}