package itemPackage;

import java.io.*;
import java.util.*;

public class food extends Item{
    String size;
    String animal;
    String type;

    public food(String name, String animal, String type) {
        this.name = name;
        this.animal = animal;
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public String getAnimal(){
        return animal;
    }

    public String getType(){
        return type;
    }

    public void setItemInfo(int today){
        Random rand = new Random();
        super.setItemInfo("FNPS " +  animal + " " + type + " (TM)",  rand.nextInt(20 - 0)*1.0, rand.nextInt(40-20)+20 * 1.0, today);
    }
}
