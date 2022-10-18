package employeePackage;

import itemPackage.*;
import java.io.*;
import java.util.*;

public class HaphazardT implements TrainType{
    public void train(Pet animal){
        System.out.println("Using haphazard training techiniques on " + animal.getName() + "!!");
        Random rand = new Random();
        if(rand.nextInt(10) == 0){
            System.out.println("Well, for better or worse, the training changed the animal's behavior...");
            if(animal instanceof Cat || animal instanceof Dog || animal instanceof Ferret) {
                if(animal.getHousebroken() == true){
                    System.out.println(animal.getName() + " the " + animal.getBreed() + " is no longer housebroken!!");
                    animal.setHousebroken(false);
                }else {
                    System.out.println(animal.getName() + " the " + animal.getBreed() + " is now housebroken!!");
                    animal.setHousebroken(true);
                }
            }
        }else{
            System.out.println("The training had no effect on the animal...");
        }
    }
}