package observerPackage;
import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;

public class leaveObs implements Subscriber{
    Employee emp; // employee performing action
    String type;
    String store; 
    Logger log;

    public leaveObs() {
        this.emp = new Employee();
        this.type = "no type yet";
    }

    @Override
    public void update(Object o) {
        this.emp = (Employee) o;
        this.display();
    }

    @Override
    public void display() {
        String pubString =  "\n ==> " + emp.getName() + " the " + type + " has left at the " + this.store +" store.\n";

        // write to log
        log.writeLog(pubString);
    }

    // setters
    public void setEmp(Employee e, String type){
        this.emp = e;
        this.type = type;
    }
    
    public void setFile(Logger l) {
        this.log = l;
    }

    public void setStore(String local) {
        this.store = local;
    }
}
