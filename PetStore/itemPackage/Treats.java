package itemPackage;

import java.io.*;
import java.util.*;

public class Treats extends Item{

    String animal;

    public Treats(String animal) {
        this.animal = animal;
    }

    public String getAnimal(){
        return animal;
    }

    public void setItemInfo(int today){
        Random rand = new Random();
        super.setItemInfo("FNPS " + animal + " Treats (TM)", rand.nextInt(10 - 0)*1.0, rand.nextInt(20-10)+10 * 1.0, today);

    }




}
