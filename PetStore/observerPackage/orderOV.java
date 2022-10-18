package observerPackage;

import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;

public class orderOV extends Subject{
    private int orders; // register value
    
    public orderOV() {
        this.orders = 0;
    }

    
    @Override
    public void notifySubs(){
        for (int i = 0; i < subs.size(); i++){
            subs.get(i).update(this.orders);
        }
    }

    public int getVal() {
        return this.orders;
    }


    public void setVal(int newVal) {
        if (newVal != orders) {
            this.orders = newVal;
            notifySubs();
        }
        
    }
}
