package com.example.budget.sevices.imlp;

import com.example.budget.model.Transaction;
import com.example.budget.sevices.BudegetSevice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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
   @Override
    public Long addTransaction(Transaction transaction){
        Map<Long, Transaction> monthTransactions = transactions.getOrDefault(LocalDate.now().getMonth(), new LinkedHashMap<>());
        monthTransactions.put(lastId, transaction);
        transactions.put(LocalDate.now().getMonth(), monthTransactions);
        return  lastId++;
    }
 @Override
    public  Transaction getTransaction(long id){
        for (Map <Long, Transaction> transactionsByMoth : transactions.values()){
        Transaction transaction  = transactionsByMoth.get(id);
        if ( transaction != null){
            return transaction;
        }
        }
        return  null;
    }
    @Override
    public  Transaction editTransaction(long id, Transaction transaction){
        for (Map <Long, Transaction> transactionsByMoth : transactions.values()){
            if (transactionsByMoth.containsKey(id)){
                transactionsByMoth.put(id, transaction);
                // transaction.setCategory(newTransaction.getCategory()); Возможный вариант
                return transaction;
            }
        }
        return  null;
    }
    @Override
    public  boolean deleteTransaction(long id){
        for (Map <Long, Transaction> transactionsByMoth : transactions.values()){
            if (transactionsByMoth.containsKey(id)){
                transactionsByMoth.remove(id);

                return true;
            }
        }
        return  false;
    }
    @Override
    public  void deleteAllTransaction(){
        transactions = new TreeMap<>();
    }
    @Override
    public  int getDailyBalance(){
     return DAILY_BUDGET * LocalDate.now().getDayOfMonth() - getAllSpend();
    }
    @Override
    public int getAllSpend(){
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
