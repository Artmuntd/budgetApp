package com.example.budget.sevices;

public interface BudegetSevice {
    int getDailyBudget();

    int getBalance();

    int getVacatoinBonus(int daysCount);

    int getSalaryWithVacation(int vacationDaysCount, int vacatoinWorkingDays, int workingDaysInMonth);
}
