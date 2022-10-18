package observerPackage;

import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;

public class visitsOV extends Subject{
    private int visits; // # of customer visits
    
    public visitsOV() {
        this.visits = 0;
    }

    
    @Override
    public void notifySubs(){
        for (int i = 0; i < subs.size(); i++){
            subs.get(i).update(this.visits);
        }
    }

    public int getVisits() {
        return this.visits;
    }


    public void setVisit(int newVal) {
        if (visits != newVal) {
            this.visits = newVal;
            notifySubs();
        }
        
    }
}
