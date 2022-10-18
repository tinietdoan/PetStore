import Items.*;
import Friends.*;
import Events.*;
import Actions.*;
import java.util.*;
import PlayGame.*;


public class main{
    public static void main(String args[]){
        Game game = new Game();
        game.buildEnv();
        // game.demo(); // only for demo video
        game.Start(5);

//        System.out.println("Program Start!");
//        //Items Unit Test Suite:
//        ConcreteItemFactory fact = new ConcreteItemFactory();
//        //Seeds:
//
//        Seed s1 = (Seed)fact.createItem(new String[]{"Seed", "LettuceSeed", "3.50", "5", "LettucePlant"});
//        Seed s2 = (Seed)fact.createItem(new String[]{"Seed", "OnionSeed", "3.50", "5", "OnionPlant"});
//        Seed s3 = (Seed)fact.createItem(new String[]{"Seed", "CarrotSeed", "3.50", "5", "CarrotPlant"});
//        System.out.println("Seed: " + s1.getName() + ". Price: " + s1.getPrice() + ". Days till grown: "
//                + s1.getCurGrowthDay() + " out of " + s1.getDaysToGrow());
//        s1.incrCurGrowthDay();
//        System.out.println("Growing for a day ... Days till grown: "
//                + s1.getCurGrowthDay() + " out of " + s1.getDaysToGrow());
//        Plant pl4 = s1.growIntoPlant(fact);
//        System.out.println("Plant: " + pl4.getName() + ". Price: " + pl4.getPrice() + ". Days till harvest: "
//                + pl4.getCurGrowthDay() + " out of " + pl4.getDaysTillHar() + ". Harvesting this plant will " +
//                "remove it from a garden: " + pl4.getHarRemPlant());
//        pl4.incrCurGrowthDay();
//        System.out.println("Growing for a day ... Days till harvest: "
//                + pl4.getCurGrowthDay() + " out of " + pl4.getDaysTillHar());
//
//
//
//        //Produce:
//        Produce p1 = (Produce)fact.createItem(new String[]{"Produce", "Lettuce", ".50"});
//        Produce p2 = (Produce)fact.createItem(new String[]{"Produce", "Onions", "1.00"});
//        Produce cp3 = (Produce)fact.createItem(new String[]{"Produce", "Carrots", ".50"});
//
//        System.out.println("Produce: " + p1.getName() + ". Price: " + p1.getPrice() + ". ");
//        //Tools: Need to implement ActionType First
//
//        //Plant:
//        Plant pl1 = (Plant)fact.createItem(new String[]{"Plant", "CarrotPlant", "5.00", "4", "true" });
//        Plant pl2 = (Plant)fact.createItem(new String[]{"Plant", "LettucePlant", "5.00", "5", "false"});
//        Plant pl3 = (Plant)fact.createItem(new String[]{"Plant", "OnionPlant", "3.00", "6", "true"});
//
//        System.out.println("Plant: " + pl1.getName() + ". Price: " + pl1.getPrice() + ". Days till harvest: "
//                + pl1.getCurGrowthDay() + " out of " + pl1.getDaysTillHar() + ". Harvesting this plant will " +
//                "remove it from a garden: " + pl1.getHarRemPlant());
//        pl1.incrCurGrowthDay();
//        System.out.println("Growing for a day ... Days till harvest: "
//                + pl1.getCurGrowthDay() + " out of " + pl1.getDaysTillHar());
//
//
//        ArrayList<Yield> pl1Yield = pl3.harvest(fact);
//
//
//
//        for(int i = 0 ; i < pl1Yield.size(); i ++){
//            System.out.println("Yields of " + pl3.getName() + ": " + pl1Yield.get(i).getName());
//        }
//
//        //Tools:
//        Tool t1 = (Tool)fact.createItem(new String[]{"Tool", "Trowel", "10.00"});
//        Tool t2 = (Tool)fact.createItem(new String[]{"Tool", "Big Bucket", "10.00"});
//        Tool t3 = (Tool)fact.createItem(new String[]{"Tool", "Large Sign", "10.00"});
//        Tool t4 = (Tool)fact.createItem(new String[]{"Tool", "Sharp Clippers", "10.00"});
//        System.out.println("This tool is a " + t1.getName()+ ". It can " + t1.getActionStr());
//        System.out.println("This tool is a " + t2.getName()+ ". It can " + t2.getActionStr());
//        System.out.println("This tool is a " + t3.getName()+ ". It can " + t3.getActionStr());
//        System.out.println("This tool is a " + t4.getName()+ ". It can " + t4.getActionStr());
//
//
//        // Garden Plot:
//        int gPlots_num = 0;
//        GardenPlot gPlot1 = new GardenPlot();
//        gPlots_num++;
//        GardenPlot gPlot2 = new GardenPlot();
//        gPlots_num++;
//        GardenPlot gPlot3 = new GardenPlot();
//        gPlot3.setGardenID(gPlots_num);
//        gPlots_num++;
//        gPlot3.waterPlot();
//        System.out.println("Current dampness = " + gPlot3.getDampness());
//        gPlot3.decr_Dampness();
//        System.out.println("Dampness after \"one day\" = " + gPlot3.getDampness() +"\n");
//
//
//
//        // Testing friends + actions
//        WaterPlants waterAction = new WaterPlants();
//        Fundraise fundraise = new Fundraise();
//        PullWeeds pullweeds = new PullWeeds();
//        HarvestPlants harvestAction = new HarvestPlants();
//        PlantSeeds plantSeeds = new PlantSeeds();
//
//        Friend bruce = new Friend();
//        bruce.setName("Bruce");
//        bruce.addMoodString("Yay, I love watering plants.");
//        bruce.addMoodString("Time flies when I'm watering, doesn't it.");
//        bruce.addMoodString("Get a good drink there, pal!.");
//        bruce.addRegString("Weather's nicetoday, innit buddy?\n");
//        bruce.addRegString("Howdy, partner! Hope the farm's good to ya today.");
//        bruce.addRegString("Rough day in the office, eh?.");
//
//        System.out.println("Interacting with Bruce:\n");
//        bruce.interact();
//        bruce.interact();
//        bruce.interact();
//
//        waterAction.doAction(pl1);
//        harvestAction.doAction(pl1);
//        plantSeeds.doAction(s1);
//
//        bruce.setProficient(waterAction);
//        System.out.println("Bruce performs actions:\n");
//        bruce.setAction(waterAction);
//        Plant plant01 = (Plant) bruce.DoAction(pl1);
//        System.out.println("Plant watered: " + plant01.getName() + ". Days till harvest: "+ plant01.getCurGrowthDay());
//
//        bruce.setAction(fundraise);
//        Object tempObj = new Object();
//        Double amount = (Double) bruce.DoAction(tempObj);
//        System.out.println("Amount fundraised = "+ amount);
//
//        bruce.setAction(harvestAction);
//        Plant plant02 = (Plant) bruce.DoAction(pl2);
//        // plant02.harvest(fact);
//
//        Seed seed01 = s1;
//        System.out.println("Seed before planted: " + seed01.getName() + ". Days Till Harvest: " + seed01.getDaysToGrow());
//        bruce.setAction(plantSeeds);
//        seed01 = (Seed) bruce.DoAction(s1);
//        System.out.println("Seed after planted: " + seed01.getName() + ". Days Till Harvest: " + seed01.getDaysToGrow());
//
//        int moodPnts = bruce.getMood();
//        System.out.println("Bruce's mood should be 1: " + moodPnts);


        //Events
//        Event e1 = new baseEvent();
//        Event e2 = new friendEvent(e1, bruce);
//        System.out.println(e2.getDesc());
//        Event e3 = new modifierEvent(e2, "Water", .5, 3);
//        System.out.println(e3.getDesc());
//        Event e4 = new oneOffEvent(e3, 2.0, "Market Prices");
//        System.out.println(e4.getDesc());
//        System.out.println(((modifierEvent)e4.getBaseEvent()).getModSubject());
    }

}

