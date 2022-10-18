package observerPackage;

import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;

public class visitsObs implements Subscriber{
    int visits; // register val
    Logger log; 
    String store;

    public visitsObs() {
        this.visits = 0;
    }

    @Override
    public void update(Object o) {
        this.visits = (int) o;
        this.display();
    }

    @Override
    public void display() {
       String pubString = "\n ==> At the end of today, the total number of the customer visits is now: " + visits + "\n";

       log.writeLog(pubString);
    }

    public void setVisits(int v){
        this.visits = v;
    }

    // setters
    public void setFile(Logger l) {
        this.log = l;
    }

    public void setStore(String local) {
        this.store = local;
    }
}
