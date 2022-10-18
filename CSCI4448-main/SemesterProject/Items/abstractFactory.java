package Items;
import java.lang.Double;

//This class, in tandem with the concrete factory class are the factory pattern implementation for the item
//class hierarchy

public abstract class abstractFactory{


    //This function takes a list of args, and produces an item accordingly
    //Depending on the object type, the item will be produced differently,
    // and the appropriate information will be added
    public Item createItem(String args[]){
        itemDataHolder idh = new itemDataHolder();
        Item tempItem = instantiateItem(args);
        tempItem.setItemInfo(args[1], Double.parseDouble(args[2]));
        if(tempItem instanceof Seed){
            ((Seed)tempItem).setPlantInfo(idh.getPlantInfo(args[4]));
        }
        if(tempItem instanceof Plant){
            ((Plant)tempItem).setYieldNames(idh.getYieldNames(args[1]));
        }
        if(tempItem instanceof Tool){
            ((Tool)tempItem).setProf();
        }
        //System.out.println("Producing obj");
        return tempItem;
    }

    abstract Item instantiateItem(String args[]);
}