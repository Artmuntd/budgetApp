package com.example.budget.sevices;

import com.example.budget.model.Transaction;

public interface BudegetSevice {
    int getDailyBudget();

    int getBalance();

    Long addTransaction(Transaction transaction);

    Transaction getTransaction(long id);

    Transaction editTransaction(long id, Transaction transaction);

    boolean deleteTransaction(long id);

    void deleteAllTransaction();

    int getDailyBalance();

    int getAllSpend();

    int getVacatoinBonus(int daysCount);

    int getSalaryWithVacation(int vacationDaysCount, int vacatoinWorkingDays, int workingDaysInMonth);
}
