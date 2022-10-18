package itemPackage;
import java.io.*;

// part of Decorator Pattern
public class Insurance extends addOnDecorator{
    addOn adds;
    
    public Insurance(addOn adds){
        this.adds = adds;
    }
    
    public String getDescript(){
        return "insurance";
    }

    public double extraCost(){
        return adds.extraCost() + 50.0;
    }
}
