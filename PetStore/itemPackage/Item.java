package itemPackage;

import java.io.*;

// Encapsulation: the traits and methods of Items are wrapped together in a single class
public abstract class Item{
    String name;
    Double purchasePrice;
    Double listPrice;
    Double salePrice;
    int dayArrived;
    int daySold;


    //Fully parameterized constuctor
    public Item(String Name, Double Purchase, Double ListP, int dayArr){
        name = Name;
        purchasePrice = Purchase;
        listPrice = ListP;
        dayArrived = dayArr;
    }

    //No parameter constructor
    public Item(){
        name = "";
        purchasePrice = -1.0;
        listPrice = -1.0;
        dayArrived = 0;
    }


    //all-value setter method
    public void setItemInfo(String Name, Double Purchase, Double ListP, int dayArr){
        name = Name;
        purchasePrice = Purchase;
        listPrice = ListP;
        dayArrived = dayArr;
    }

    //setters for specifically salePrice and day sold:
    public void setSaleInfo(Double salePrice, int daySold){
        this.salePrice = salePrice;
        this.daySold = daySold;
    }

//getters:
    public String getName(){
        return name;
    }

    public Double getPurchasePrice(){
        return purchasePrice;
    }

    public Double getListPrice(){
        return listPrice;
    }

    public Double getSalePrice(){
        return salePrice;
    }

    public int getDayArrived(){
        return dayArrived;
    }

    public int getDaySold(){
        return daySold;
    }

    public abstract void setItemInfo(int day);

}