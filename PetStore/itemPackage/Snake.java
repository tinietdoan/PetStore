package itemPackage;

import java.io.*;

public class Snake extends Pet implements PetInterface{
    String size;

    //unparameterized constructor
    public Snake(){
        this.size = "";
    }

    //parameterized constructor
    public Snake(String size){
        this.size = size;
    }

    //getters
    public String getSize(){
        return size;
    }
    
    //setters
    public void setSize(String size){
        this.size = size;
    }


    public void setPetInfo(){
        InfoGenerator ig = new InfoGenerator();
        super.setPetInfo(ig.getInfo("snakeBreed"), ig.getNum(1,15), true);
    };
    public void setItemInfo(int today){
        InfoGenerator ig = new InfoGenerator();
        super.setItemInfo(ig.getInfo("name"), ig.getNum(0,300)*1.0, ig.getNum(300,500)*1.0, today);
    };
    
}
