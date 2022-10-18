package itemPackage;
import java.io.*;

public abstract class addOn{
    String descript = "Unknown addOn";

    public String getDescript(){
        return this.descript;
    }

    public abstract double extraCost();
}
