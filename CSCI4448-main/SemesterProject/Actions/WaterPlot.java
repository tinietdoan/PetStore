package Actions;

import Items.*;
import Friends.*;

public class WaterPlot implements actionType {
    @Override
    public void doAction(Friend f) {

        System.out.println("\n==> "+f.getName() + " has watered the plants! They are one day closer to being harvested!\n");
        return; 
    }
    @Override
    public String toString(){
        return "Water Plot";
    }
}
