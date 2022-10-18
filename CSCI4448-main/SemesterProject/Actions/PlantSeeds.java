package Actions;

import Items.*;
import Friends.*;

public class PlantSeeds implements actionType {
    @Override
    public void doAction(Friend f) {
        System.out.println("\n==> "+f.getName() + " has planted seeds!");
        return;
    }
    @Override
    public String toString(){
        return "Plant Seeds";
    }
}
