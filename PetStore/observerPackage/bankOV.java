package observerPackage;

import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;

public class bankOV extends Subject{
    private double val; // register value
    
    public bankOV() {
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
