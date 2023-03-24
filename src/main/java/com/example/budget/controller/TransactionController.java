package com.example.budget.controller;

import com.example.budget.model.Transaction;
import com.example.budget.sevices.BudegetSevice;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Транзакции", description = "GRUD операции и другие")
public class TransactionController {
    private final BudegetSevice budegetSevice;

    public TransactionController(BudegetSevice budegetSevice) {
        this.budegetSevice = budegetSevice;
    }

    @PostMapping
    public ResponseEntity<Long> addTransaction(@RequestBody Transaction transaction){
    long id =   budegetSevice.addTransaction(transaction);
    return ResponseEntity.ok().body(id);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable  long id){
      Transaction transaction = budegetSevice.getTransaction(id);
      if(transaction == null) {
        return   ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transaction);
    }
    @PutMapping("/{id}")
    public  ResponseEntity<Transaction> editTransaction(@PathVariable long id,@RequestBody Transaction transaction) {
        Transaction newTransaction = budegetSevice.editTransaction(id, transaction);
        if (newTransaction == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newTransaction);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable long id){
     if  (budegetSevice.deleteTransaction(id)) {
         return  ResponseEntity.ok().build();
     }
     return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public  ResponseEntity<Void> deleteAllTransaction(){
       budegetSevice.deleteAllTransaction();
       return ResponseEntity.ok().build();
    }
}
