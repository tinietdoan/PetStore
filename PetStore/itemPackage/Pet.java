package itemPackage;

import java.io.*;

public class Pet extends Item{

    String breed;
    int age;
    boolean healthy;

    //pet parameterized constructor
    public Pet(String breed, int age, boolean healthy){
        this.breed = breed;
        this.age = age;
        this.healthy = healthy;
    }

    //pet non-parameterized constructor
    public Pet(){
        breed = "";
        age = -1;
        healthy = false;
    }

    //pet value setter method
    public void setPetInfo(String breed, int age, boolean healthy){
        this.breed = breed;
        this.age = age;
        this.healthy = healthy;
    }


    //getters
    public String getBreed(){
        return breed;
    }
    public int getAge(){
        return age;
    }

    public boolean getHealthy(){
        return healthy;
    }

    public void toSick(){
        healthy = false;
    }

    public void toHealthy(){
        healthy = true;
    }

    public void setHousebroken(boolean x){System.out.println("This shouldnt be called....");}
    public boolean getHousebroken(){System.out.println("This shouldnt be called...."); return false;}

    public void setItemInfo(int x){System.out.println("This should never be called.");}


}