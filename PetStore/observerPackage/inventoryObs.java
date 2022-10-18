package observerPackage;
import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;

public class inventoryObs implements Subscriber{
    int count; // number of items in inventory
    String store;
    Logger log;

    public inventoryObs() {
        this.count = 0;
        this.store = "";
    }

    @Override
    public void update(Object o) {
        this.count = (int) o;
        this.display();
    }

    @Override
    public void display() {
        String pubString = "\n==> New items have arrived at the "+ store + 
            " location! The total amount added is now: " + count + "\n";

        // write to log
        log.writeLog(pubString);
    }

    public void setFile(Logger l) {
        this.log = l;
    }

    public void setStore(String location) {
        this.store = location;
    }

}
