package itemPackage;

import java.io.*;
import java.util.*;


public class Leash extends Item{

    String animal;

    public Leash(String animal) {
        this.animal = animal;
    }

    public String getAnimal(){
        return animal;
    }

    public void setItemInfo(int day){
        Random rand = new Random();
        super.setItemInfo("FNPS " + animal + " Leash (TM)",  rand.nextInt(20 - 0)*1.0, rand.nextInt(40-20)+20 * 1.0, day);
    }
}
