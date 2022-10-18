package employeePackage;

import itemPackage.*;
import java.io.*;
import java.util.*;

public class NegitiveRenfT implements TrainType{
    public void train(Pet animal){
        System.out.println("Using negitive renforcement training techiniques on " + animal.getName() + "!!");
        Random rand = new Random();
        if(animal instanceof Cat || animal instanceof Dog || animal instanceof Ferret) {
            if (rand.nextInt(5) == 0) {
                System.out.println("Oh no! " + animal.getName() + " the " + animal.getBreed() + " is no longer housebroken!!");
                animal.setHousebroken(false);
            } else if (rand.nextInt(5) > 1) {
                System.out.println(animal.getName() + " the " + animal.getBreed() + " is now housebroken!!");
                animal.setHousebroken(true);
            } else {
                System.out.println("The training had no effect on the animal...");
            }
        }else{
            System.out.println("The training had no effect on the animal...");
        }
    }
}