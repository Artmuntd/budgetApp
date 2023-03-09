package com.example.budget.sevices.imlp;

import com.example.budget.sevices.BudegetSevice;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Service
public class BudegetSeviceImpl implements BudegetSevice {

    public  static  final  int SALARY = 20_000;
  //  public  static  final  int  AVG_SALARY =  (10000+10000+10000+10000+10000+15000+15000+15000+15000+15000+15000+20000) / 12;
     public  static  final  int  AVG_SALARY =  SALARY / 12;
    public  static  final  double  AVG_DAYS = 29.3;
    @Override
    public int getDailyBudget() {
        return SALARY / 30;
    }

    @Override
    public int getBalance() {
        return SALARY - LocalDate.now().getDayOfMonth() * getDailyBudget();
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
