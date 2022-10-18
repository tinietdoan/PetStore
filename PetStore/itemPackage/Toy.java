package itemPackage;

import java.io.*;
import java.util.*;

public class Toy extends Item{

    String animal;

    public Toy(String animal) {
        this.animal = animal;
    }

    public String getAnimal(){
        return animal;
    }

    public void setItemInfo(int today){
        Random rand = new Random();
        super.setItemInfo("FNPS " + animal + " Toy (TM)", rand.nextInt(20 - 0)*1.0, rand.nextInt(40-20)+20 * 1.0, today);

    }




}



