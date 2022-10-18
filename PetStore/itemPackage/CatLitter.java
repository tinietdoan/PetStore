package itemPackage;

import java.io.*;
import java.util.*;

public class CatLitter extends Item{

    String size;

    public CatLitter(String size) {
        this.size = size;
    }

    public String getSize(){
        return size;
    }

    public void setItemInfo(int today){
        Random rand = new Random();
        super.setItemInfo("FNPS Cat Litter (TM)", rand.nextInt(20 - 0)*1.0, rand.nextInt(40-20)+20 * 1.0, today);
    }

}