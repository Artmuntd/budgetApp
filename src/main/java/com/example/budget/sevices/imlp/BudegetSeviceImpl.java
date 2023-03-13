package com.example.budget.sevices.imlp;

import com.example.budget.model.Transaction;
import com.example.budget.sevices.BudegetSevice;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
public class BudegetSeviceImpl implements BudegetSevice {

    public  static  final  int SALARY = 35_000 - 9_240;
    public static  final int SAVING = 3_000;

    public static final  int DAILY_BUDGET = (SALARY - SAVING) / LocalDate.now().lengthOfMonth();

    public  static  int balance = 0;
  //  public  static  final  int  AVG_SALARY =  (10000+10000+10000+10000+10000+15000+15000+15000+15000+15000+15000+20000) / 12;
     public  static  final  int  AVG_SALARY =  SALARY / 12;
    public  static  final  double  AVG_DAYS = 29.3;

    private static  Map<Month,Map<Long, Transaction>> transactions = new TreeMap<>();
    private  static  long lastId =0;
    @Override
    public int getDailyBudget() {
        return DAILY_BUDGET;
    }

    @Override
    public int getBalance() {
     return  SALARY - SAVING - getAllSpend();
    }
    public void addTransaction(Transaction transaction){
        Map<Long, Transaction> monthTransactions = transactions.getOrDefault(LocalDate.now().getMonth(), new LinkedHashMap<>());
        monthTransactions.put(lastId++, transaction);
    }

    public  int getDailyBalance(){
     return DAILY_BUDGET * LocalDate.now().getDayOfMonth() - getAllSpend();
    }

    private  int getAllSpend(){
        Map<Long, Transaction> monthTransactions = transactions.getOrDefault(LocalDate.now().getMonth(), new LinkedHashMap<>());
        int sum=0;
        for (Transaction transaction : monthTransactions.values()) {
            sum+= transaction.getSum();
        }
        return  sum;
    }
    @Override
    public  int getVacatoinBonus (int daysCount){

    double avgDaySalary = AVG_SALARY / AVG_DAYS;
    return (int) (daysCount * avgDaySalary);
    }
    @Override
    public  int getSalaryWithVacation(int vacationDaysCount, int vacatoinWorkingDays, int workingDaysInMonth){

     getVacatoinBonus(vacationDaysCount);
     int salary = SALARY /  workingDaysInMonth * (workingDaysInMonth - vacationDaysCount);
     return salary + getVacatoinBonus(vacationDaysCount);

    }


}
