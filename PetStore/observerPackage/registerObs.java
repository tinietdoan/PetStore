package observerPackage;

import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;

public class registerObs implements Subscriber{
    double reg; // value of register
    Employee emp; // employee performing action
    Logger log;
    String store;

    public registerObs() {
        this.reg = 0.0;
    }

    @Override
    public void update(Object o) {
        this.reg = (double) o;
        this.display();
    }

    @Override
    public void display() {
        String pubString = "\n ==> " + this.emp.getName() + " the clerk has counted the " + this.store + 
            " register. The total value of the register is: $" + reg + "\n";

        // write to log
        log.writeLog(pubString);
    }

    // setters
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
