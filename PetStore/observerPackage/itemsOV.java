package observerPackage;

import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;

public class itemsOV extends Subject{
    private int itemNum; // total number of pets and supplies in inventory
    
    public itemsOV() {
        this.itemNum = 0;
    }

    
    @Override
    public void notifySubs(){
        for (int i = 0; i < subs.size(); i++){
            subs.get(i).update(itemNum);
        }
    }

    public int getItemNum() {
        return this.itemNum;
    }


    public void setItemNum(int newVal) {
        if (newVal != itemNum) {
            this.itemNum = newVal;
            notifySubs();
        }
        
    }
}
