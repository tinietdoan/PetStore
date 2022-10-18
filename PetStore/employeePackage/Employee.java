package employeePackage;

import itemPackage.*;
import java.io.*;
import java.util.*;


public class Employee{
    String name;
    int daysWorkedConsec;

    //Constructors:
    public Employee(String name){
        this.name = name;
        daysWorkedConsec = 0;
    }

    public Employee() {
        name = "<not yet written>";
        daysWorkedConsec = 0;
    }


    //Getters setters and incrementers
    public String getName(){
        return name;
    }

    public int getDaysWorked(){
        return daysWorkedConsec;
    }

    public void incrDaysWorked() {
        daysWorkedConsec++;
    }

    public void resetDaysWorked(){
        daysWorkedConsec = 0;
    }

    public boolean isSick(){
        Random rand = new Random();
        if(rand.nextInt(10) == 1){
            System.out.println(name + " is feeling sick today and will not come in....");
            return true;
        }else{
            return false;
        }
    }

    public void trainAnimal(Pet animal){System.out.println("This function should not be called");}
    //Main functions:






}