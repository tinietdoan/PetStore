package observerPackage;
import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;

public class arriveObs implements Subscriber{
    Employee emp; // employee performing action
    String type; // employee type: clerk or trainer
    String store; // store location
    Logger log;

    public arriveObs() {
        this.emp = new Employee();
        this.type = "no type yet";
        this.store = "no store yet";
    }


    @Override
    public void update(Object o) {
        this.emp = (Employee) o;
        this.display();
    }

    @Override
    public void display() {
        String pubString = "\n==> " +  emp.getName() + " the " + type + " has arrived at the "+ store + "\n";
        
        // write to log
        log.writeLog(pubString);
    }

    // setters
    public void setFile(Logger l) {
        this.log = l;
    }

    public void setEmp(Employee e, String type){
        this.emp = e;
        this.type = type;
    }

    public void setStore(String local) {
        this.store = local;
    }
}
