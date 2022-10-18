package observerPackage;

import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;

public class salesOV extends Subject
{
    private ArrayList <Employee> emps; // list of all employees
    private ArrayList <Double> empSales;
    int changeIdx;
    
    public salesOV(Clerk[] clerks, Trainer[] trainers) {
        emps = new ArrayList<>();
        Collections.addAll(emps, clerks);
        Collections.addAll(emps, trainers);

        for (int i = 0; i < emps.size(); i++) {
            empSales.add(0.0);
        }

        changeIdx =0;
    }

    
    @Override
    public void notifySubs(){
        for (int i = 0; i < subs.size(); i++){
            subs.get(i).update(this.changeIdx);
        }
    }


    public Double getSales(int idx) {
        return empSales.get(idx);
    }


    public void setSale(Employee e, double newSale) {
        if (emps.contains(e)) {
            int idx = emps.indexOf(e);
            double oldSales = empSales.get(idx);
            empSales.set(idx, oldSales + newSale);
            this.changeIdx = idx;
            notifySubs();
        }
    }
}
