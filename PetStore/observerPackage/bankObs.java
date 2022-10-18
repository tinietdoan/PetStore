package observerPackage;

import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;

public class bankObs implements Subscriber {
    double val; // register val
    Employee emp; // employee performing action
    Logger log;
    String store;


    public bankObs() {
        this.val = 0.0;
    }

    @Override
    public void update(Object o) {
        this.val = (double) o;
        this.display();
    }

    @Override
    public void display() {
        String pubString = "\n==> " + this.emp.getName() + " the clerk has left " + store + 
            " to go to the Bank. The total value of the register is now: " + val + "\n";


        log.writeLog(pubString);
    }

    public void setFile(Logger l) {
        this.log = l;
    }

    public void setEmp(Employee e){
        this.emp = e;
    }

    public void setStore(String local) {
        this.store = local;
    }
}
