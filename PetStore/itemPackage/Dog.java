package itemPackage;

import java.io.*;

// Inheritance: Dog is a subclass of Pet, which is a subclass of Item
// This means that Dog inherits the traits of Pet and of Item
public class Dog extends Pet implements PetInterface{

    String size;
    String color;
    boolean housebroken;
    boolean purebred;

    //parameterized constructor
    public Dog(String size, String color, boolean housebroken, boolean purebred){
        this.size = size;
        this.color = color;
        this.housebroken = housebroken;
        this.purebred = purebred;
    }


    //getters
    public String getSize(){
        return size;
    }
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
        super.setPetInfo(ig.getInfo("dogBreed"), ig.getNum(1,15), true);
    };
    public void setItemInfo(int today){
        InfoGenerator ig = new InfoGenerator();
        super.setItemInfo(ig.getInfo("name"), ig.getNum(0,300)*1.0, ig.getNum(300,500)*1.0, today);
    };



}