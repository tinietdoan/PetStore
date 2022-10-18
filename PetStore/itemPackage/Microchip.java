package itemPackage;
import java.io.*;

// part of Decorator Pattern
public class Microchip extends addOnDecorator{
    addOn adds;
    
    public Microchip(addOn adds){
        this.adds = adds;
    }

    public String getDescript(){
        return "microchip";
    }

    public double extraCost(){
        return adds.extraCost() + 50.0;
    }
}
