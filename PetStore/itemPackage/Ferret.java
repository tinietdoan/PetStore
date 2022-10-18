package itemPackage;

import java.io.*;

public class Ferret extends Pet implements PetInterface{
    String color;
    boolean housebroken;

    //unparameterized constructor
    public Ferret(){
        this.color = "";
        this.housebroken = false;
    }

    //parameterized constructor
    public Ferret(String color, boolean housebroken){
        this.color = color;
        this.housebroken = housebroken;
    }

    //getters
    public String getColor(){
        return color;
    }
    public boolean getHousebroken(){
        return housebroken;
    }
    
    //setters
    public void setColor(String color){
        this.color = color;
    }
    public void setHousebroken(boolean isHouseBroke){
        this.housebroken = isHouseBroke;
    }
    public void setFerretInfo(String color, boolean houseBroke) {
        this.color = color;
        this.housebroken = houseBroke;
    }


    public void setPetInfo(){
        InfoGenerator ig = new InfoGenerator();
        super.setPetInfo(ig.getInfo("dogBreed"), ig.getNum(1,15), true);
    };
    public void setItemInfo(int today){
        InfoGenerator ig = new InfoGenerator();
        super.setItemInfo(ig.getInfo("name"), ig.getNum(0,300)*1.0, ig.getNum(300,500)*1.0, today);
    };
    
}
