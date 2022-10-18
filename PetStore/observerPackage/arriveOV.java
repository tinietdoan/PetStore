package observerPackage;

import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;

public class arriveOV extends Subject{
    private Employee emp; // register value
    
    public arriveOV() {
        this.emp = new Employee();
    }

    
    @Override
    public void notifySubs(){
        for (int i = 0; i < subs.size(); i++){
            subs.get(i).update(this.emp);
        }
    }

    public Employee getEmp() {
        return this.emp;
    }


    public void setEmp(Employee e) {
        this.emp = e;
        notifySubs();
        
    }
}
