package observerPackage;

import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;


public class Subject {
    List<Subscriber> subs = new ArrayList<>();
    boolean isChanged;

    // subscribe and unsubscribe
    public void register(Subscriber sub) {
        subs.add(sub);
    }

    public void remove_sub(Subscriber sub) {
        subs.remove(sub);
    }

    // notify subscribers
    public void notifySubs(){
        System.out.println("notifySubs() not yet implemented");
        // for (int i = 0; i < subs.size(); i++){
        //     subs.get(i).update();
        // }
    }

    //set change
    public void setChange(boolean TF) {
        this.isChanged = TF;
    }

    public boolean hasChanged() {
        return isChanged;
    }

}
