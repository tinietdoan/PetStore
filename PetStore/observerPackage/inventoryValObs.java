package observerPackage;
import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;

public class inventoryValObs implements Subscriber{
    double val; // number of items in inventory
    Employee emp; // employee performing action
    String store;
    Logger log;

    public inventoryValObs() {
        this.val = 0.0;
    }

    @Override
    public void update(Object o) {
        this.val = (double) o;
        this.display();
    }

    @Override
    public void display() {
        String pubString = "\n==> " + this.emp.getName() + " the clerk has just done inventory at " + this.store + 
            ". The total value of the inventory is currently: " + val + "\n";

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
