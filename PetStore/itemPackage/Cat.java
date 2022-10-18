package itemPackage;

import java.io.*;

public class Cat extends Pet implements PetInterface{

    String color;
    boolean housebroken;
    boolean purebred;

    //parameterized constructor
    public Cat(String color, boolean housebroken, boolean purebred){
        this.color = color;
        this.housebroken = housebroken;
        this.purebred = purebred;
    }


    //getters
    public String getColor(){
        return color;
    }
    public boolean getHousebroken(){
        return housebroken;
    }
    public void setHousebroken(boolean x){housebroken = x;}
    public boolean getPurebred(){
        return purebred;
    }

    public void setPetInfo(){
        InfoGenerator ig = new InfoGenerator();
        super.setPetInfo(ig.getInfo("catBreed"), ig.getNum(1,17), true);
    };
    public void setItemInfo(int today){
        InfoGenerator ig = new InfoGenerator();
        super.setItemInfo(ig.getInfo("name"), ig.getNum(0,300)*1.0, ig.getNum(300,500)*1.0, today);
    };



}