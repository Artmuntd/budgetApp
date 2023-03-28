package com.example.budget.sevices.imlp;

import com.example.budget.model.Transaction;
import com.example.budget.sevices.BudegetSevice;
import com.example.budget.sevices.FileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class BudegetSeviceImpl implements BudegetSevice {

    final private FileService fileService;
    public  static  final  int SALARY = 35_000 - 9_240;
    public static  final int SAVING = 3_000;

    public static final  int DAILY_BUDGET = (SALARY - SAVING) / LocalDate.now().lengthOfMonth();

    public  static  int balance = 0;
  //  public  static  final  int  AVG_SALARY =  (10000+10000+10000+10000+10000+15000+15000+15000+15000+15000+15000+20000) / 12;
     public  static  final  int  AVG_SALARY =  SALARY / 12;
    public  static  final  double  AVG_DAYS = 29.3;

    private static  TreeMap<Month,LinkedHashMap<Long, Transaction>> transactions = new TreeMap<>();
    private  static  long lastId =0;

    public BudegetSeviceImpl(FileService fileService) {
        this.fileService = fileService;
    }
    @PostConstruct
    private void init(){
      readFromFile();
    }

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
        LinkedHashMap<Long, Transaction> monthTransactions = transactions.getOrDefault(LocalDate.now().getMonth(), new LinkedHashMap<>());
        monthTransactions.put(lastId, transaction);
        transactions.put(LocalDate.now().getMonth(), monthTransactions);
        saveToFile();
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
                saveToFile();
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
    public Path creatMonthLyReport(Month month) throws IOException {
       LinkedHashMap<Long, Transaction> monthlyTransactions  = transactions.getOrDefault(month, new LinkedHashMap<>());
        Path path= fileService.creatTempFile("monthlyReport");
       for(Transaction transaction: monthlyTransactions.values()); {
           try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)){
               writer.append(transactions.getCategory() + transactions.getS);
           }
        }
    }
    private void  saveToFile(){
        try {
      String json = new ObjectMapper().writeValueAsString(transactions);
       fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private  void  readFromFile(){
   String json =  fileService.readFromFile();
        try {
         transactions =  new ObjectMapper().readValue(json, new TypeReference<TreeMap<Month,LinkedHashMap<Long, Transaction>>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
