package observerPackage;

import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;

//Observable: keeps track of number of items being added to the inventory + value of inventory
public class inventoryValue extends Subject{
    private double val; // number of items in inventory
    
    public inventoryValue() {
        this.val = 0.0;
    }

    
    @Override
    public void notifySubs(){
        for (int i = 0; i < subs.size(); i++){
            subs.get(i).update(this.val);
        }
    }

    public double getVal() {
        return this.val;
    }


    public void setVal(double newVal) {
        if (newVal != val) {
            this.val = newVal;
            notifySubs();
        }
        
    }
}
