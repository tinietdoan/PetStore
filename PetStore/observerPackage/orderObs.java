package observerPackage;

import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;

public class orderObs implements Subscriber{
    int orders; // register val
    Employee emp; // employee performing action
    String store;
    Logger log;

    public orderObs() {
        this.orders = 0;
    }

    @Override
    public void update(Object o) {
        this.orders = (int) o;
        this.display();
    }

    @Override
    public void display() {
        String pubString = "\n ==> " + this.emp.getName() + " the clerk has placed an order for " + this.store +
            ". The total number of the items is now: " + orders + "\n";
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
