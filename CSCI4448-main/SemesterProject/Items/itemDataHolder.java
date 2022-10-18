package Items;
import java.util.*;
import Actions.*;

//This class holds all of the instance information.
//It has the information of what items cost, what they grown into, and what they produce when harvested

public class itemDataHolder{


    //Given a name, produce the arguments of the plant
    //Needs Balance Patch
    public String[] getPlantInfo(String plantName) {
        //System.out.println("Aquiring plant info");
        switch (plantName) {
            case "CarrotPlant":
                return new String[]{"Plant", "CarrotPlant", "5.00", "4", "true"};


            case "OnionPlant":
                return new String[]{"Plant", "OnionPlant","6.00", "5", "true"};


            case "PotatoPlant":
                return new String[]{"Plant", "PotatoPlant","5.00", "5", "true"};


            case "LettucePlant":
                return new String[]{"Plant", "LettucePlant","5.00", "5", "false"};


            case "TomatoPlant":
                return new String[]{"Plant", "TomatoPlant","5.00", "5", "false"};


            case "BerryPlant":
                return new String[]{"Plant", "BerryPlant","5.00", "5", "false"};


            case "LavenderPlant":
                return new String[]{"Plant", "LavenderPlant","5.00", "5", "false"};


            default:
                System.out.println("Enter Valid Plantname. Got " + plantName + " From ItemDataHolder");
                return null;

        }
    }


    //given a name, produce the arguments of the yield
    public String[] getYieldInfo(String yName) {
        //System.out.println(yName);
//returns item type, name, price, ...
        switch (yName){
            case "CarrotSeed":
            return new String[]{"Seed", "CarrotSeed", "5.00", "4", "CarrotPlant"};


            case "OnionSeed":
                //System.out.println("hucnh");
            return new String[]{"Seed", "OnionSeed","6.00", "5", "OnionPlant"};


            case "PotatoSeed":
            return new String[]{"Seed", "PotatoSeed","5.00", "5", "PotatoPlant"};


            case "LettuceSeed":
            return new String[]{"Seed", "LettuceSeed","5.00", "5", "LettucePlant"};


            case "TomatoSeed":
            return new String[]{"Seed", "TomatoSeed","5.00", "5", "TomatoPlant"};


            case "BerrySeed":
            return new String[]{"Seed", "BerrySeed","5.00", "5", "BerryPlant"};


            case "LavenderSeed":
            return new String[]{"Seed", "LavenderSeed","5.00", "5", "LavenderPlant"};

            case "Carrots":
                return new String[]{"Produce", "Carrots", "7.00"};


            case "Onions":
                return new String[]{"Produce", "Onions","9.00"};


            case "Potatoes":
                return new String[]{"Produce", "Potatoes","5.00"};


            case "Lettuce":
                return new String[]{"Produce", "Lettuce","5.00"};


            case "Tomatoes":
                return new String[]{"Produce", "Tomatoes","5.00"};


            case "Berries":
                return new String[]{"Produce", "Berries","5.00"};


            case "Lavender":
                return new String[]{"Produce", "Lavender","5.00"};

            default:
                System.out.println("Enter Valid yield name. " + yName + " From ItemDataHolder");
                return null;

            }
        }

        //Given a name, produce the types of yield given by a harvested plant
        public ArrayList<String> getYieldNames(String plantName){

            switch (plantName) {
                case "CarrotPlant":
                    return new ArrayList<String>(Arrays.asList("Carrots"));


                case "OnionPlant":
                    //System.out.println("hucnh");
                    return new ArrayList<String>(Arrays.asList("Onions", "OnionSeed"));


                case "PotatoPlant":
                    return new ArrayList<String>(Arrays.asList("Potatoes", "PotatoSeed"));


                case "LettucePlant":
                    return new ArrayList<String>(Arrays.asList("Lettuce"));


                case "TomatoPlant":
                    return new ArrayList<String>(Arrays.asList("Tomatoes", "TomatoSeeds"));


                case "BerryPlant":
                    return new ArrayList<String>(Arrays.asList("Berries", "BerrySeed"));


                case "LavenderPlant":
                    return new ArrayList<String>(Arrays.asList("Lavender"));


                default:
                    System.out.println("Enter Valid plant names for yield id. " + plantName + " From ItemDataHolder");
                    return null;

            }
        }


        //given a name, produce an action type to be given to a tool
        public actionType getActionType(String toolName){
            switch (toolName){
                case "Trowel":
                    return new PlantSeeds();

                case "Big Bucket":
                    return new WaterPlot();

                case "Large Sign":
                    return new Fundraise();

                case "Sharp Clippers":
                    return new HarvestPlants();

               case "Grippy Gloves":
                   return new PullWeeds();

                default:
                    System.out.println("Error: Enter valid Tool name: " + toolName + ". From itemdataholder" );
                    return null;
            }
        }
}
