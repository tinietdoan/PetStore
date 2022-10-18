package Items;


//This class is the final stage of the plant cycle.
//Produce can be sold to the market for money

public class Produce extends Item implements Yield {
    String type;

    public Produce(){
        type = "produce";
    }

    @Override
    public String getType() {
        return type;
    }

}