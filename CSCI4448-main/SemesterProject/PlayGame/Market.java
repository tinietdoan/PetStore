package PlayGame;

import Items.*;

import java.sql.Array;
import java.util.*;


//The market class allows the player to interact with the market system.
//Through this, the players can buy and sell items.
//The stock of the market rotates daily.

//the market has a variable sale, which acts as a multiplier for the prices of the items sold. They can be discounts or markups.

public class Market {
    ConcreteItemFactory fact;
    ArrayList<Seed> seeds;
    ArrayList<Tool> tools;
    itemDataHolder idh;
    Double sale;


    public Market(ConcreteItemFactory fact){
        this.fact = fact;
        this.seeds = new ArrayList<Seed>();
        this.tools = new ArrayList<Tool>();
        this.idh = new itemDataHolder();
        this.sale = 1.0;
    }

    public void setSale(Double rate){
        sale = rate;
    }


    public void displaySeeds() {
        String compString = "";
        for (int i = 0; i < seeds.size(); i++) {
            compString = compString + i + ") " + seeds.get(i).getSeedName() + " selling for $" + seeds.get(i).getPrice()*sale + ". \n";
        }
        System.out.println(compString);

    }

    public void displayTools(){
            String compString = "";
            for (int i = 0; i < tools.size(); i++) {
                compString = compString + (i+1) + ") " + tools.get(i).getName() + " selling for $" + tools.get(i).getPrice()*sale + ". \n";
            }
            System.out.println(compString);
    }

    public void getInv(){
        this.seeds.clear();
        this.tools.clear();
        String[] seeds = new String[]{"CarrotSeed", "OnionSeed", "PotatoSeed", "LettuceSeed","TomatoSeed", "BerrySeed", "LavenderSeed"};
        String[] tools = new String[]{"Trowel", "Big Bucket", "Large Sign", "Sharp Clippers", "Grippy Gloves"};
        Random rand = new Random();

        for(int i = 0 ; i < 4; i ++){
            String args[] = idh.getYieldInfo(seeds[rand.nextInt(7)]);
            Seed ts = (Seed)fact.createItem(args);

            this.seeds.add(ts);
        }
         //System.out.println("Loc2 : " + Fargs[0]);
        for(int i = 0 ; i < 3; i ++){
            String args[] = new String[]{"Tool", tools[rand.nextInt(5)], "9.0"};
            this.tools.add((Tool)fact.createItem(args));
        }
    }

    public Seed getSeed(int ind){
        return seeds.get(ind);
    }

    public Tool getTool(int ind){
        return tools.get(ind);
    }

    public void buySeed(Game game, int ind){
        if(!(game.player.getMoney() < getSeed(ind).getPrice())){
            game.seeds.add(getSeed(ind));
            game.player.addMoney(-1*getSeed(ind).getPrice());
            System.out.println("\n==> "+"You bought the " + getSeed(ind).getSeedName());
        }else {
            System.out.println("\n==> "+"Not enough funds to by the item...");
        }
    }

    public void buyTool(Game game, int ind){
        if(!(game.player.getMoney() < getTool(ind).getPrice())){
            game.tools.add(getTool(ind));
            game.player.addMoney(-1*getTool(ind).getPrice());
            System.out.println("\n==> "+"You bought the " + getTool(ind).getName());
        }else{
            System.out.println("\n==> "+"Not enough funds to by the item...");
        }

    }

    public void buyPlot(Game game){
        if(!(game.player.getMoney() < 20.0)){
            game.plots.add(new GardenPlot());
            System.out.println("\n==> "+"You bought a new plot.");
        }else{
            System.out.println("\n==> "+"Not enough funds to by a new plot... $20 needed");
        }
    }

    public void sellSeed(Game game, Seed seed){
        System.out.println("\n==> "+seed.getSeedName() + "has been sold!");
        game.player.addMoney(seed.getPrice());
        game.seeds.remove(seed);
    }

    public void sellProduce(Game game, Produce prod){
        System.out.println("\n==> "+prod.getName() + " has been sold!");
        game.player.addMoney(prod.getPrice());
        game.produce.remove(prod);
    }

}
