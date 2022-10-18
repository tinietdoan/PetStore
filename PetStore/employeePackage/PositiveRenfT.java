package employeePackage;

import itemPackage.*;
import java.io.*;
import java.util.*;

public class PositiveRenfT implements TrainType{
    public void train(Pet animal){
        System.out.println("Using positive renforcement training techiniques on " + animal.getName() + "!!");
        Random rand = new Random();
        if(rand.nextInt(2) == 0){
            System.out.println("Training has succeeded!");
            if(animal instanceof Cat || animal instanceof Dog || animal instanceof Ferret) {
                System.out.println(animal.getName() + " the " + animal.getBreed() + " is now housebroken!!");
                animal.setHousebroken(true);
            }
        }else{
            System.out.println("Training has failed...");
        }
    }
}