package Items;
import java.util.*;

//Plants are the middle form of the seed-plant-yield hierarchy
//Given a number of days, a plant will become harvestable
//When harvested a plant will produce yield


public class Plant extends Item{
    int daysForHarvest;
    int curDay;
    boolean harvestRemovesPlant;
    ArrayList<String> yieldNames;
    //Yield is part of the strategy pattern.
    //The behavior of the different possible yield objects is delegated outside of the primary plant structure.
    ArrayList<Yield> yield;

    public Plant(int dFH, boolean hRP){
        this.daysForHarvest = dFH;
        this.harvestRemovesPlant = hRP;
        this.curDay = 0;
    }

    public int getDaysForHar(){
        return daysForHarvest;
    }
    public int getCurGrowthDay(){ return curDay;}
    public void incrCurGrowthDay(){curDay++;}
    public boolean getHarRemPlant(){
        return harvestRemovesPlant;
    }

    void setYieldNames(ArrayList<String> yn){
        yieldNames = yn;
    }

    public void setHRP(boolean TF) {
        this.harvestRemovesPlant = TF;
    }

    public void resetCurGrowthDay() {
        curDay = 0;
    }

    public boolean isReady() {
        if (curDay >= daysForHarvest) {
            return true;
        }
        return false;
    }

    //This function uses the factory to produce the appropriate yield items for the plant.
    public ArrayList<Yield> harvest(ConcreteItemFactory fact){
        ArrayList<Yield> retList = new ArrayList<Yield>();
        itemDataHolder idh = new itemDataHolder();
        for(int i = 0; i < yieldNames.size(); i ++){  //For each yield name
            String args[] = idh.getYieldInfo(yieldNames.get(i));
            //System.out.println("Loc2 : " + Fargs[0]);
            Item curItem = fact.createItem(args); //create the yield, and add it to the yield list.
            if(curItem instanceof Produce){
                retList.add((Produce)curItem);
            }else{
                retList.add((Seed)curItem);
            }

        }
        resetCurGrowthDay();
        return retList;
    }
}