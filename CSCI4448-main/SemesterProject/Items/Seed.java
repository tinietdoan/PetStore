package Items;

//This is the first phase of the plant system.
//Can be planted to be grown into a plant

public class Seed extends Item implements Yield{
    int daysToGrow;
    int curGrowthDay;
    String plantName;
    String plantArgs[];
    boolean isPlanted;
    String type;

    public Seed(int DTG, String pl){
        type = "seed";
        daysToGrow = DTG;
        curGrowthDay = 0;
        plantName = pl;
        isPlanted = false;
        plantArgs = new String[5];
    }

    @Override
    public String getName() {
        return plantName;
    }
    @Override
    public String getType() {
        return type;
    }
    
    public String getSeedName() {
        return name;
    }
    public int getDaysToGrow(){
        return daysToGrow;
    }
    public int getCurGrowthDay(){
        return curGrowthDay;
    }
    public void incrCurGrowthDay(){
        curGrowthDay ++;
    }

    public void setPlantInfo(String args[]){
        //System.out.println("Plant info: price: " + args[0] + ". DTH: " + args[1] + ". RPH: " + args[2]);
        plantArgs = args;
    }

    public void plant(){
        isPlanted = true;
    }

    //This function uses factory to create a fully instantiated version of the grown plant.
    public Plant growIntoPlant(ConcreteItemFactory fact){
        return (Plant)fact.createItem(plantArgs);
    }

}