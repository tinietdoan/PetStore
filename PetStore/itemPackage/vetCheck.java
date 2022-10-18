package itemPackage;
import java.io.*;

// part of Decorator Pattern
public class vetCheck extends addOnDecorator{
    addOn adds;
    
    public vetCheck(addOn adds){
        this.adds = adds;
    }

    public String getDescript(){
        return "pre-paid vet check";
    }

    public double extraCost(){
        return adds.extraCost() + 50.0;
    }
}
