package observerPackage;

import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;


public class registerOV extends Subject{
    private double reg; // value in register
    
    public registerOV() {
        this.reg = 0.0;
    }

    
    @Override
    public void notifySubs(){
        for (int i = 0; i < subs.size(); i++){
            subs.get(i).update(this.reg);
        }
    }

    public double getReg() {
        return this.reg;
    }


    public void setReg(double newVal) {
        if (newVal != reg) {
            this.reg = newVal;
            notifySubs();
        }
    }
}
