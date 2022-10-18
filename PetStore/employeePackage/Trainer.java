package employeePackage;

import itemPackage.*;
import java.io.*;
import java.util.*;

public class Trainer extends Employee{

    //This is an example of the strategy pattern!!
    //The interface is called TrainType
    //The concrete algorithms that can be used here are
    //HaphazardT, NegitiveRenfT, and PositiveRenfT
    TrainType trainStrat;

    public Trainer(String name, TrainType trainStrat){
        super(name);
        this.trainStrat = trainStrat;
    }

    //Strategy applicator
    public void trainAnimal(Pet animal){
        trainStrat.train(animal);
    }
}