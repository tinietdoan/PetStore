package observerPackage;

import itemPackage.*;
import employeePackage.*;
import java.io.*;
import java.util.*;

public class escapeOV extends Subject{
    private Pet escaped; // register value
    
    public escapeOV() {
        this.escaped = new Pet();
    }

    
    @Override
    public void notifySubs(){
        for (int i = 0; i < subs.size(); i++){
            subs.get(i).update(this.escaped);
        }
    }

    public Pet getEscaped() {
        return this.escaped;
    }


    public void setEscaped(Pet newVal) {
        
        this.escaped = newVal;
        notifySubs();
        
    }
}
