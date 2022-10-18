package observerPackage;

import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;

//Observable: keeps track of number of items being added to the inventory + value of inventory
public class inventoryCount extends Subject{
    private int count; // number of items in inventory
    
    public inventoryCount() {
        this.count = 0;
    }
    
    @Override
    public void notifySubs(){
        for (int i = 0; i < subs.size(); i++){
            subs.get(i).update(this.count);
        }
    }

    public int getCount() {
        return this.count;
    }


    public void setCount(int cnt) {
        this.count = cnt;
        notifySubs();
    }
}
