package itemPackage;

import java.io.*;

public class Bird extends Pet implements PetInterface{

    String size;
    boolean mimicry;
    boolean exotic;
    boolean papers;

    //parameterized constructor
    public Bird(String size, boolean mimicry, boolean exotic, boolean papers){
        this.size = size;
        this.mimicry = mimicry;
        this.exotic = exotic;
        this.papers = papers;
    }


    //getters
    public String getSize(){
        return size;
    }
    public boolean getMimicry(){
        return mimicry;
    }
    public boolean getExotic(){
        return exotic;
    }
    public boolean getPapers(){
        return papers;
    }


    public void setPetInfo(){
        InfoGenerator ig = new InfoGenerator();
        super.setPetInfo(ig.getInfo("birdBreed"), ig.getNum(1,15), true);
    };
    public void setItemInfo(int today){
        InfoGenerator ig = new InfoGenerator();
        super.setItemInfo(ig.getInfo("name"), ig.getNum(0,300)*1.0, ig.getNum(300,500)*1.0, today);
    };

}