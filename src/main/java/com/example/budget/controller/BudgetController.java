package com.example.budget.controller;

import com.example.budget.sevices.BudegetSevice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/budget")
public class BudgetController {

     private BudegetSevice budegetSevice;

    public BudgetController(BudegetSevice budegetSevice) {
        this.budegetSevice = budegetSevice;
    }

    @GetMapping("/daily")
    public  int dailyBudget(){
        return budegetSevice.getDailyBudget();
    }
    @GetMapping("/balance")
    public  int balance(){
        return  budegetSevice.getBalance();
    }
    @GetMapping("/vacation")
    public  int vacationBonus(@RequestParam int vacationDays){
        return budegetSevice.getVacatoinBonus(vacationDays);
    }
    @GetMapping("/vacation/salary")
    public  int salaryWithVacation(@RequestParam int vacationDays, @RequestParam int workingDays, @RequestParam int vacWorkDays) {
        return  budegetSevice.getSalaryWithVacation(vacationDays,vacWorkDays,workingDays);
    }
}
