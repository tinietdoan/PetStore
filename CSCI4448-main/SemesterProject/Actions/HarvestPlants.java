package Actions;

import java.util.*;
import Items.*;
import Friends.*;

public class HarvestPlants implements actionType{
    @Override
    public void doAction(Friend f) {
        System.out.println("\n==> "+f.getName() + " has harvested the plants!");
        return;
    }
    @Override
    public String toString(){
        return "Harvest Plants";
    }
}
