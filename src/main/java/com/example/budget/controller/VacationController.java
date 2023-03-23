package com.example.budget.controller;

import com.example.budget.sevices.BudegetSevice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vacation")
public class VacationController {

     private final BudegetSevice budegetSevice;

    public VacationController(BudegetSevice budegetSevice) {
        this.budegetSevice = budegetSevice;
    }

    @GetMapping("")
    public  int vacationBonus(@RequestParam int vacationDays){
        return budegetSevice.getVacatoinBonus(vacationDays);
    }
    @GetMapping("/salary")
    public  int salaryWithVacation(@RequestParam int vacationDays, @RequestParam int workingDays, @RequestParam int vacWorkDays) {
        return  budegetSevice.getSalaryWithVacation(vacationDays,vacWorkDays,workingDays);
    }
}
