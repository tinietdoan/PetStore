package observerPackage;

import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;

public class itemsObs implements Subscriber{
    int itemNum; // rnumber of pets + supplies
    Employee emp; // employee performing action
    Logger log;
    String store;

    public itemsObs() {
        this.itemNum = 0;
    }

    @Override
    public void update(Object o) {
        this.itemNum = (int) o;
        this.display();
    }

    @Override
    public void display() {
        String pubString = "\n ==> " + this.emp.getName() + " the clerk has done inventory for "+ this.store + 
            ". The total number of pets + supplies is: " + itemNum + "\n";

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
