package Items;
import java.lang.Integer;
import java.lang.Boolean;


//This class, in tandem with the abstract factory class are the factory pattern implementation for the item
//class hierarchy
public class ConcreteItemFactory extends abstractFactory{


    //This function takes the list of arguments passed in and creates a base level item
    public Item instantiateItem(String args[]){
        switch(args[0]){
            case "Tool":
                //args[3] = profficiency;
                Tool retTool = new Tool();
                return retTool;

            case "Plant":
                //args[3] days to harvest
                //args[4] removes plant on yield
                Plant retPlant = new Plant(Integer.parseInt(args[3]),Boolean.parseBoolean(args[4]));
                return retPlant;

            case "Seed":
                //args[3] = days to grow.
                //args[4] = Plantname
                //System.out.println(args[4] + " Is the plantname");
                Seed retSeed = new Seed(Integer.parseInt(args[3]), args[4]);
                return retSeed;

            case "Produce":
                Produce retProduce = new Produce();
                return retProduce;

            default:
                System.out.println("Factory: Must enter valid item type. Got: " + args[0]);
        }
        return null;

    }




}