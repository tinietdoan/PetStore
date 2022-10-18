package observerPackage;

import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;

public class escapeObs implements Subscriber{
    Pet escaped; // register val
    Employee emp; // employee performing action
    String type; // employee type
    Logger log;
    String store;

    public escapeObs() {
        this.escaped= new Pet();
    }

    @Override
    public void update(Object o) {
        this.escaped = (Pet) o;
        this.display();
    }

    @Override
    public void display() {
        Pet currPet = this.escaped;

        String pubString = "\n ==>" +  currPet.getName() + " the " + currPet.getBreed() + " has escaped at " + this.store + 
            "! No worries, " + this.emp.getName() + " the " + this.type + " caught " + currPet.getName() + "\n";
        
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
