package Items;

import java.util.ArrayList;



//Garden plots hold the grown plants, and give a space for the seeds to grow.
//They have an amount of moister to reflect if the plants are watered
//They have a finite amount of space


public class GardenPlot {
    ArrayList<Plant> plants; // plants in plot
    ArrayList<Seed> seeds; // seeds in plot
    int dampness;
    int gardenID;
    int totalSpace;
    int availSpace;
    Double price;
    Boolean weeds;
    ConcreteItemFactory fact = new ConcreteItemFactory();



    public GardenPlot() {
        plants = new ArrayList<Plant>();
        seeds = new ArrayList<Seed>();
        totalSpace = 4; // each garden plot can only hold up to 4 plants/seeds
        availSpace = 4;
        dampness = 0;
        weeds = false;
    }

    // =========== Setters ===========
    public void setDampness(int d) {
        this.dampness = d;
        return;
    }
    public void setTotSpace(int tot) {
        this.totalSpace = tot;
        return;
    }
    public void setAvailSpace(int a) {
        this.availSpace = a;
        return;
    }
    public void setPrice(Double price) {
        this.price = price;
        return;
    }
    public void setWeeds(Boolean TF) {
        this.weeds = TF;
        return;
    }
    public void setGardenID(int gardenNums) {
        this.gardenID = gardenNums + 1;
        return;
    }
    

    // =========== Getters ===========
    public ArrayList<Plant> getPlants(){
        return plants;
    }
    public ArrayList<Seed> getSeeds(){
        return seeds;
    }
    public int getDampness() {
        return dampness;
    }

    public int getGardenID() {
        return gardenID;
    }

    public int getTotalSpace () {
        return totalSpace;
    }
    public int getAvailSpace(){
        return availSpace;
    }

    /* =========== hasWeeds() =========== */
    public boolean hasWeeds() {
        return weeds;
    }

    
    /* =========== addSeed() =========== */
    // add new seed to the plot
    public void addSeed(Seed toPlant) {
        // check for available space
        if (availSpace == 0) {
            System.out.println("\n==> "+"Garden plot #" + gardenID + " is full! Unable to plant item...\n");
            return;
        }

        seeds.add(toPlant);
        availSpace--;
        return;
    }

    /* =========== addPlant() =========== */
    // add new plant to the plot
    public void addPlant(Plant pl) {
        // check for available space
        if (availSpace == 0) {
            System.out.println("\n==> "+"Garden plot #" + gardenID + " is full! Unable to plant item...\n");
            return;
        }

        plants.add(pl);
        availSpace--;
        return;
    }

    /* ===========removeSeed() =========== */
    //remove seed from list
    public void removeSeed(Seed s) {
        seeds.remove(s);
        availSpace++;
    }

    /* ===========removeSeed =========== */
    //remove plant from list
    public void removePlant(Plant pl) {
        plants.remove(pl);
        availSpace++;
    }

    // check to see if the plot is full
    public boolean isFull(){
        if (availSpace == 0) {
            return true;
        }

        return false;
    }


    /* =========== waterPlot() =========== */
    // water the plot (increasing dampness) and increases growth of plants and seeds
    public void waterPlot(){
        this.dampness = 2; // dampness remains only 2 days after watering

        increaseGrowth();
    }

    // dampness will be decreased every day
    public void decr_Dampness(){
        if (dampness == 0) {
            return;
        }
        dampness--;
    }

    public void increaseGrowth(){
        for (int i = 0; i < plants.size(); i++) {
            plants.get(i).incrCurGrowthDay();

            if (plants.get(i).isReady()) {
                System.out.println("\n==> "+plants.get(i).getName() +  " in garden #" + gardenID + " is ready for Harvest!");
            }
        }
        for (int i = 0; i < seeds.size(); i++) {
            seeds.get(i).incrCurGrowthDay();
            if (seeds.get(i).getDaysToGrow() <= seeds.get(i).getCurGrowthDay()) {
                Seed toGrow = seeds.get(i);
                System.out.println("\n==> "+toGrow.getName() + "in garden #" + gardenID + " has grown into a plant!");
                Plant newPlant = toGrow.growIntoPlant(fact);
                removeSeed(toGrow);
                addPlant(newPlant);
            }
        }
    }

    public void displayGarden(){
        for (int i = 0; i < plants.size(); i++) {
            System.out.println("==> " + plants.get(i).getName());
        }
        for (int i = 0; i < seeds.size(); i++) {
            System.out.println("==> " + seeds.get(i).getSeedName());
        }
    }
}
