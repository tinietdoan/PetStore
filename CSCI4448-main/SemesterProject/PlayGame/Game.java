package PlayGame;

import Items.*;
import Events.*;
import Friends.*;
import Actions.*;
import java.util.*;

//This is the main driver class. While we tried to avoid a 'god-class', a certain level of orchestration is needed for
//the game. This class provides that structure.


// Singleton Pattern
public class Game {
    int day;
    ArrayList<Event> activeEvents; // will keep track of any active events (3-day sale in market, one-off drought for the day, etc.)
    ArrayList<GardenPlot> plots; // all of the gardens owned by the player
    ArrayList<Seed> seeds; // all of the unplanted seeds the player owns
    ArrayList<Produce> produce; // all of hte roduce the player has
    ArrayList<Tool> tools; // all of the tools the player owns
    ArrayList<Friend> Characters; // will hold all of the characters belonging to the game (that have no been befriended)
    ArrayList<Friend> Friends; // will hold all of the Player's friends that work on their farm
    Player player; 
    boolean taskDelegated; // will tell us whether the task being perfromed is delegated to a friend or not
    Menu menu;
    boolean[] achievements ; // list of pre-determined achievements to calculate the player's score
    int[] pointmap;
    EventGetter eventGetter; // hold events hat may occur during game play
    Market market; // Market for buy/sell
    int friendIdx;

    ConcreteItemFactory fact = new ConcreteItemFactory(); // factory fro making items (plants, seeds, tools, produce)
    WaterPlot waterAction = new WaterPlot(); // water action  
    Fundraise fundraise = new Fundraise(); // fundraise action
    PullWeeds pullweeds = new PullWeeds(); // pull weeds action
    HarvestPlants harvestAction = new HarvestPlants(); // harvest action
    PlantSeeds plantSeeds = new PlantSeeds(); // plants seeds action


    // ================================================= Game Constructor =================================================
    public Game() {
        day = 0;
        activeEvents = new ArrayList<Event>();
        plots = new ArrayList<GardenPlot>();
        seeds = new ArrayList<Seed>();
        produce = new ArrayList<Produce>();
        tools = new ArrayList<Tool>();
        Characters = new ArrayList<Friend>();
        Friends = new ArrayList<Friend>();
        player = new Player();
        taskDelegated = false;
        this.achievements = new boolean[]{false,false,false,false,false,false,false};
        this.pointmap = new int[]{1,2,3,4,5,6,7};
        this.menu = new Menu();
        this.eventGetter = new EventGetter();
        this.market = new Market(fact);
        friendIdx = 0;
    }

    // populate characters array
    public void makeCharacters(){
        Friend bruce = new Friend();
        bruce.setName("Bruce");
        bruce.addMoodString("\n==> Bruce: Yay, I love watering plants.");
        bruce.addMoodString("\n==> Bruce: Time flies when I'm watering, doesn't it.");
        bruce.addMoodString("\n==> Bruce: Get a good drink there, little plant!");
        bruce.addRegString("\n==> Bruce: Weather's nice today, innit buddy?");
        bruce.addRegString("\n==> Bruce: Howdy, partner! Hope the farm's good to ya today.");
        bruce.addRegString("\n==> Bruce: Rough day in the office, eh?.");
        bruce.setProficient(waterAction);
        Characters.add(bruce);

        Friend isabelle = new Friend();
        isabelle.setName("Isabelle");
        isabelle.addMoodString("\n==> Isabelle: Any day I'm pulling weeds is a good day.");
        isabelle.addMoodString("\n==> Isabelle: The plot just looks so darn good after the weeds have been pulled.");
        isabelle.addMoodString("\n==> Isabelle: Get a good drink there, little plant!");
        isabelle.addRegString("\n==> Isabelle: Weather's nice today, innit?");
        isabelle.addRegString("\n==> Isabelle: Howdy, partner!");
        isabelle.setProficient(pullweeds);
        Characters.add(isabelle);

        Friend steve = new Friend();
        steve.setName("Steve");
        steve.addMoodString("\n==> Steve: This is a great farm. It deserves some extra funds!");
        steve.addMoodString("\n==> Steve: I love getting out there and doing what I can to raise money for the farm.");
        steve.addRegString("\n==> Steve: Whoopdy Doopdy, what a good day.");
        steve.addRegString("\n==> Steve: Howdy, partner!");
        steve.setProficient(fundraise);
        Characters.add(steve);

        Friend barb = new Friend();
        barb.setName("Barb");
        barb.addMoodString("\n==> Barb: I can't wait for these little seeds to sprout!");
        barb.addMoodString("\n==> Barb: We're gonna take real good care of you little seeds.");
        barb.addRegString("\n==> Barb: In a singin' mood. Lalalalalala...");
        barb.addRegString("\n==> Barb: Howdy, partner!");
        barb.setProficient(plantSeeds);
        Characters.add(barb);

        Friend wyett = new Friend();
        wyett.setName("Wyett");
        wyett.addRegString("\n==> Wyett: Let's get to work, friend!");
        wyett.addRegString("\n==> Wyett: Howdy, partner!");
        Characters.add(wyett);

        Friend tinie = new Friend();
        tinie.setName("Tinie");
        tinie.addRegString("\n==> Tinie: In a singin' mood. Lalalalalala...");
        tinie.addRegString("\n==> Tinie: Howdy, partner!");
        Characters.add(tinie);
    }

    // ================================================= Getters =================================================
    public int getDay() {
        return this.day;
    }
    public ArrayList<Event> getActiveEvents(){
        return activeEvents;
    }
    public ArrayList<GardenPlot> getPlots() {
        return plots;
    }

    public ArrayList<Seed> getSeeds(){
        return seeds;
    }

    public ArrayList<Produce> getProduce() {
        return produce;
    }
    public ArrayList<Tool> getTools() {
        return tools;
    }

    public ArrayList<Friend> getFriends(){
        return Friends;
    } // will hold all of the Player's friends that work on their farm

    public boolean isTaskDelegated(){
        return taskDelegated; // this will tell us whether a task has been delegated
    }

    // ================================================= newDay() =================================================
    // At the start of each day, we need to update some things...
    public void newDay() {
        EventOccurance();
        EventHandler();

        System.out.println("\n==> You've run out of energy for the day. Time to start a new day!");
        day++;
        player.setEnergy(10); // player has 10 energy points per day
        taskDelegated = false;
        market.getInv();

        // update all of our plots
        for (int i = 0; i < plots.size(); i++) {
            plots.get(i).decr_Dampness();
            plots.get(i).increaseGrowth();
            plots.get(i).setWeeds(true);
        }
        checkAchievements(); // check if any achievements have been made
    }

    // ================================================= hasEnoughEnergy() helper =================================================
    public boolean hasEnoughEnergy(int energyNeeded) {
        if (energyNeeded > player.getEnergyPoints()) {
            return false;
        }
        return true;
    }


    // ================================================= Garden Actions  =================================================


    /* ============= PlantSeed() ============= */
    public void PlantSeed(int plotNum){
        int energyNeeded = 0;
        boolean toolOwned = false;
        for (int i = 0; i < tools.size(); i++) {
            if (tools.get(i).getActionStr().equals("Plant Seeds")) {
                toolOwned = true;
            }
        }

        // only decrement the player's energy points if they didn't delegate the task to a friend
        if (taskDelegated) {
            energyNeeded = 0;
        }
        // if they own a specific tool, use less energy points
        else if (toolOwned) {
            System.out.println("\n==> Looks like you've got  the right tools! Only one energy point used!");
            energyNeeded = 1;
        }
        else {
            energyNeeded = 2;
        }
 
        if (!hasEnoughEnergy(energyNeeded)) {
            System.out.println("\n==> You don't have enough energy to finish do this task!");
            return;
        }


        if (plots.get(plotNum).isFull()) {
            System.out.println("\n==> This plot is all full! Select another plot or harvest some plants to make room. No energy points used\n");
            return;
        }

        if (seeds.size() == 0) {
            System.out.println("\n==> You have no seeds to plant. Purchase some in the store or get seeds by harvesting. No energy points used\n");
            return;
        }
        player.decre_Energy(energyNeeded);

        seeds.get(0).plant();
        plots.get(plotNum).addSeed(seeds.get(0));
        System.out.println("\n==> "+seeds.get(0).getName() + " has been planted in Garden plot #" + (plotNum + 1));
        seeds.remove(0); // once the seed is planted, the seed will be removed from the seed list

    }

    /* ============= WaterPlot() ============= */
    // watering a plot makes the garden damp AND waters the plants & seeds (which helps them grow faster)
    public void WaterPlot(int plotNum) {
        int energyNeeded = 0;
        boolean toolOwned = false;
        for (int i = 0; i < tools.size(); i++) {
            if (tools.get(i).getActionStr().equals("Water Plot")) {
                toolOwned = true;
            }
        }

        //only decrement the player's energy points if they didn't delegate the task to a friend
        if (taskDelegated) {
            energyNeeded = 0;
        }
        // if they own a specific tool, use less energy points
        else if (toolOwned) {
            System.out.println("\n==> Looks like you've got  the right tools! Only one energy point used!");
            energyNeeded = 1;
        }
        else {
            energyNeeded = 2;
        }

        if (player.getEnergyPoints() < energyNeeded) {
            System.out.println("\n==> "+"You don't have enough energy to finish do this task!");
            return;
        }
        player.decre_Energy(energyNeeded);

        
        System.out.println("\n==> "+"Garden #" + (plotNum+1) +" has been watered!");
        plots.get(plotNum).waterPlot();
    }


    /* ============= Fundraise() ============= */
    public void Fundraise() {
        int energyNeeded = 0;

        //only decrement the player's energy points if they didn't delegate the task to a friend
        if (taskDelegated) {
            energyNeeded = 0;
        }
        else {
            energyNeeded = 1;
        }
        if (!hasEnoughEnergy(energyNeeded)) {
            System.out.println("\n==> "+"You don't have enough energy to finish do this task!");
            return;
        }
        player.decre_Energy(energyNeeded);
        
        Double total = 0.0;
        Random rand = new Random();

        // 5% chance of $100
        if (rand.nextInt(100) <=5) {
            total = 100.0;
        }
        // 30% chance of $40
        else if(rand.nextInt(100) <= 30) {
            total = 40.0;
        }
        // 50% chance of $20
        else if (rand.nextInt(100) <= 50) {
            total = 20.0;
        }
        
        System.out.println("\n==> "+"$" + total + " has been fundraised.\n");
        // otherwise return $0
        player.money += total;


        // 50% chance of fundraiser friend event being triggered (only if it hasn't been triggered yet)
        if (eventGetter.getTriggered()[3] == false) {
            if (rand.nextInt(100) <= 50) {
                if (Characters.size() == 0) {
                    return;
                }
                Friend newFriend = Characters.get(friendIdx);
                friendIdx++;
                Event fundraiseFriend = eventGetter.getE4(newFriend);
                activeEvents.add(fundraiseFriend);
                System.out.println("\n==> "+newFriend.getName() + " was impressed by your fundraising! They have become your friend.");
                EventHandler();
            }
        }
        return;
    }


    /* ============= HarvestPlot() ============= */
    // will harvest an entire plot at once ( only plants that are ready fro harvest, of course)
    public void HarvestPlot(int plotNum) {
        int energyNeeded = 0;
        boolean toolOwned = false;
        for (int i = 0; i < tools.size(); i++) {
            if (tools.get(i).getActionStr().equals(" Harvest Plants")) {
                toolOwned = true;
            }
        }

        //only decrement the player's energy points if they didn't delegate the task to a friend
        if (taskDelegated) {
            energyNeeded = 0;
        }
        // if specific tool owned, use less energy points
        else if (toolOwned) {
            System.out.println("\n==> Looks like you've got  the right tools! Only one energy point used!");
            energyNeeded = 1;
        }
        else {
            energyNeeded = 2;
        }

        if (!hasEnoughEnergy(energyNeeded)) {
            System.out.println("\n==> "+"You don't have enough energy to finish do this task!");
            return;
        }

        // harvest handling
        ArrayList<Plant> plants_in_plot = plots.get(plotNum).getPlants();
        ArrayList<Yield> thisYield = new ArrayList<Yield>();
        for (int i = 0; i < plants_in_plot.size(); i++) {

            // if ready for harvest, then harvest and add to yield list
            if (plants_in_plot.get(i).getCurGrowthDay() >= plants_in_plot.get(i).getDaysForHar()) {
                System.out.println("\n==> "+plants_in_plot.get(i).getName() + " is being harvested!");
                thisYield.addAll(plants_in_plot.get(i).harvest(fact));
                plots.get(plotNum).getPlants().get(i).resetCurGrowthDay();

                // if harvesting removes the plant, then remove it
                if (plants_in_plot.get(i).getHarRemPlant()) {
                    System.out.println("\n==> "+"Because harvesting " + plants_in_plot.get(i).getName() + " requires us to remove the whole plant, it's no longer in the garden.");
                    plots.get(plotNum).removePlant(plants_in_plot.get(i));
                }
                else {
                    System.out.println("\n==> "+plants_in_plot.get(i).getName() + " doesn't get uprooted during harvest so it's still in the garden, but needs to regrow!");
                }
            }  
        }

        if (thisYield.size() == 0) {
            System.out.println("\n==> "+"There were no plants ready for harvest in this plot. No energy points used.");
            return;
        }
        player.decre_Energy(energyNeeded);
        
        // populate seeds and produce arrays
        for (int i = 0; i < thisYield.size(); i++) {
            if (thisYield.get(i) instanceof Seed) {
                seeds.add((Seed) thisYield.get(i));
            }
            else if (thisYield.get(i) instanceof Produce) {
                produce.add((Produce) thisYield.get(i));
            }
        }
        System.out.println("\n==> "+"Check you inventory to see what the harvest has yielded!");
        return;
    }

    /* ============= PullWeeds() ============= */
    public void PullWeeds(int plotNum) {
        if (!plots.get(plotNum).hasWeeds()) {
            System.out.println("\n==> "+"This plot has no weeds to pull! No energy points used.");
            return;
        }

        int energyNeeded = 0;
        boolean toolOwned = false;
        for (int i = 0; i < tools.size(); i++) {
            if (tools.get(i).getActionStr().equals("Pull Weeds")) {
                toolOwned = true;
            }
        }

        //only decrement the player's energy points if they didn't delegate the task to a friend
        if (taskDelegated) {
            energyNeeded = 0;
        }
        else if (toolOwned) {
            System.out.println("\n==> Looks like you've got  the right tools! Only one energy point used!");
            energyNeeded = 1;
        }
        else {
            energyNeeded = 2;
        }

        if (!hasEnoughEnergy(energyNeeded)) {
            System.out.println("\n==> "+"You don't have enough energy to finish do this task!");
            return;
        }

        player.decre_Energy(energyNeeded);
        plots.get(plotNum).setWeeds(false);
        System.out.println("\n==> "+"The weeds have been pulled from garden #" + (plotNum +1));
    }

    /* ============= Interact with a Friend ============= */
    public void interactFriend(Friend f) {
        f.interact();
    }

    /* ============= delegateTask() to a friend ============= */
    public void delegateTask(Friend f, String action, int plotNum) {
        int energyNeeded = 1;
        if (!hasEnoughEnergy(energyNeeded)) {
            System.out.println("\n==> "+"You don't have enough energy to finish do this task!");
            return;
        }
        player.decre_Energy(energyNeeded);
        taskDelegated = true;

        if (action.equals("harvest")) {
            f.setAction(harvestAction);
            f.DoAction();
            HarvestPlot(plotNum);
        }
        else if (action.equals("water")) {
            f.setAction(waterAction);
            f.DoAction();
            WaterPlot(plotNum);
        }
        else if (action.equals("weed")) {
            f.setAction(pullweeds);
            f.DoAction();
            PullWeeds(plotNum);
        }
        else if (action.equals("fundraise")) {
            f.setAction(fundraise);
            f.DoAction();
            Fundraise();
        }
        else if (action.equals("plant")) {
            f.setAction(plantSeeds);
            f.DoAction();
            PlantSeed(plotNum);
        }

        taskDelegated = false;
    }

    // ================================================= Add & Remove Items  =================================================

    public void addPlot(GardenPlot newPlot) {
        int sz = plots.size();
        newPlot.setGardenID(sz);
        plots.add(newPlot);
    }

    public void addProduce(Produce prod) {
        produce.add(prod);

    }
    
    public void removeProduce(Produce prod) {
        produce.remove(prod);
    }

    public void addSeeds(Seed s) {
        seeds.add(s);
    }

    public void removeSeeds(Seed s) {
        seeds.remove(s);
    }
    
    public void addTools(Tool t) {
        tools.add(t);
    }

    public void removeTools(Tool t) {
        tools.remove(t);
    }
    
    public void addFriend(Friend f) {
        Friends.add(f);
    }


    // ================================================= Calculating Score  =================================================

    /* ============= checkAchievements() ============= */
    // will determing which achievements have been completed 
    public void checkAchievements(){
        int total = 0;
        if(plots.size() >= 2){      //Achievement 1: have at least 2 plots
            achievements[0] = true;
        }

        if(Friends.size() >= 2){    //Achievment 2: have at least 2 friends
            achievements[1] = true;
        }

        if(player.getMoney() >= 100){
            achievements[2] = true;
        }

        if(Friends.size() >= 4){
            achievements[3] = true;
        }

        if(produce.size() >= 8){
            achievements[4] = true;
        }

        if(seeds.size() >= 8){
            achievements[5] = true;
        }
        if(plots.size() >= 4){
            achievements[6] = true;
        }
    }

    /* ============= getScore() ============= */
    // calculates score based on achievements
    public void getScore(){
        int total= 0;
        for(int i = 0 ; i < 7 ; i ++){
            if(achievements[i]){
                total += pointmap[i];
            }
        }
        int moodTot = 0;
        for (int j = 0; j < Friends.size(); j++) {
            moodTot += Friends.get(j).getMood();
        }

        if ((moodTot/2) > 0) {
            System.out.println("\n==> "+"Looks like you were awarded a few extra points for being a great friend to your friends and bringing up their mood!");
        }
        total += (moodTot/2); // extra points rewarded for mood

        System.out.println("\n==> "+"Your final score is: " + total + " Gold Stars!");
    }


    // ================================================= Events =================================================

    public void EventHandler(){

        for(int i = 0 ; i < activeEvents.size();i++){
            Event cur = activeEvents.get(i);
            System.out.println("\n==> "+"An Event Occured! Event ID: " + cur.getID());
            switch (cur.getID()){
                case 1:
                    System.out.println("\n==> "+"A friend has stopped by to see if you need some help.");
                    Friends.add(((friendEvent)cur).getFriend());
                    activeEvents.remove(i);
                    System.out.println(cur.getDesc());
                    break;

                case 2:
                    System.out.println("\n==> "+"There has been a change in sale prices at the Market Place.");
                    System.out.println(cur.getDesc());
                    if(!(((modifierEvent)cur).getDur() == 0)){
                        this.market.setSale(((modifierEvent)cur).getModifier());
                        ((modifierEvent)cur).decrDuration();
                    }else{
                        activeEvents.remove(i);
                    }
                    break;


                case 3:
                    System.out.println("\n==> "+"Uh-oh! A drought has un-watered your plants and gardens!");
                    for(int j = 0; j < plots.size(); j ++){
                        plots.get(j).setDampness(0);
                    }
                    activeEvents.remove(i);
                    System.out.println(cur.getDesc());
                    break;

                case 4:
                    Friends.add(((friendEvent)cur.getBaseEvent()).getFriend());
                    player.addMoney(((oneOffEvent)cur).getChange());
                    activeEvents.remove(i);
                    System.out.println(cur.getDesc());
                    break;

                case 5:
                    //System.out.println(cur.getDesc() + cur.getID());
                    System.out.println("\n==> "+"Someone was impressed by your selling skills and wants to be your friend!");
                    Friends.add(((friendEvent)(cur.getBaseEvent())).getFriend());

                    if(!(((modifierEvent)cur).getDur() == 0)){
                        this.market.setSale(((modifierEvent)cur).getModifier());
                        ((modifierEvent)cur).decrDuration();
                    }else{
                        activeEvents.remove(i);
                    }
                    System.out.println(cur.getDesc());
                    break;

                case 6:
                    System.out.println("\n==> "+"Some dang kids steal produce. One feels bad an becomes friend.");
                    for(int j = produce.size()/2 ; j > 0 ; j --){
                        produce.remove(j);
                    }
                    Friends.add(((friendEvent)cur.getBaseEvent()).getFriend());
                    activeEvents.remove(i);
                    System.out.println(cur.getDesc());
                    break;

                case 7:
                    System.out.println("\n==> "+"Swarm of Hungry Locusts clear out garden plot.");
                    plots.remove(plots.size()-1);
                    addPlot(new GardenPlot());
                    activeEvents.remove(i);
                    System.out.println(cur.getDesc());
                    break;
                case 8:
                    System.out.println("\n==> "+"Heavy Rains water all plots for two days.");
                    for(int j = 0; j < plots.size(); j ++){
                        WaterPlot(j);
                    }
                    activeEvents.remove(i);
                    System.out.println(cur.getDesc());
                    break;
                default:
                    System.out.println("\n==> "+"Error from Event Handler In The Game Object;");
                        break;
            }
        }
    }


    public void EventOccurance(){
        Random rand = new Random();
        if (!eventGetter.getTriggered()[0]) {
            if (rand.nextInt(100) <= 25) {
                Friend newFriend = Characters.get(friendIdx);
                friendIdx++;
                Event e1 = eventGetter.getE1(newFriend);
                activeEvents.add(e1);
            }
        }
        if (!eventGetter.getTriggered()[1]) {
            if (rand.nextInt(100) <= 10) {
                Event e2 = eventGetter.getE2();
                activeEvents.add(e2);
            }
        }
        if (!eventGetter.getTriggered()[2]) {
            if (rand.nextInt(100) <= 10) {
                Event e3 = eventGetter.getE3();
                activeEvents.add(e3);
            }
        }
        if (!eventGetter.getTriggered()[4]) {
            if (rand.nextInt(100) <= 8) {
                Friend newFriend = Characters.get(friendIdx);
                friendIdx++;
                Event e1 = eventGetter.getE5(newFriend);
                activeEvents.add(e1);
            }
        }
        if (!eventGetter.getTriggered()[5]) {
            if (rand.nextInt(100) <= 7) {
                Friend newFriend = Characters.get(friendIdx);
                friendIdx++;
                Event e1 = eventGetter.getE6(newFriend);
                activeEvents.add(e1);
            }
        }
        if (!eventGetter.getTriggered()[6]) {
            if (rand.nextInt(100) <= 5) {
                Event e1 = eventGetter.getE7();
                activeEvents.add(e1);
            }
        }
        if (!eventGetter.getTriggered()[7]) {
            if (rand.nextInt(100) <= 20) {

                Event e1 = eventGetter.getE8();
                activeEvents.add(e1);
            }
        }
    }


    // ================================================= Game Play =================================================
    /* ============= displayInv() helper============= */
    // for dislaying the plaer's current inventory
    public void displayInv() {
        System.out.println("\nUnplanted seeds: ");
        for (int i = 0; i < seeds.size(); i++) {
            System.out.println("==> " + seeds.get(i).getName());
        }

        System.out.println("\nTools: ");
        for (int i = 0; i < tools.size(); i++) {
            System.out.println("==> " + tools.get(i).getName());
        }

        System.out.println("\nProduce: ");
        for (int i = 0; i < produce.size(); i++) {
            System.out.println("==> " + produce.get(i).getName());
        }

        for (int i = 0; i < plots.size(); i ++) {
            System.out.println("\nIn Garden Plot " + (i+1) + " there are...");
            plots.get(i).displayGarden();
        }
    }

    /* ============= demo() ============= */
    // THIS IS ONLY USED FOR DEMO
    // initializes many variables so that we don't have to start from the beginning
    public void demo() {
        itemDataHolder idh = new itemDataHolder();

        String[] plantNames = new String[]{"CarrotPlant", "OnionPlant"};

        for (int i = 0; i < 2; i++){
            String[] tempPlantArgs = idh.getPlantInfo(plantNames[i]);
            Plant newPlant = (Plant) fact.createItem(tempPlantArgs);
            plots.get(0).addPlant(newPlant);
        }
        Seed s1 = (Seed)fact.createItem(new String[]{"Seed", "LettuceSeed", "3.50", "5", "LettucePlant"});
        plots.get(0).addSeed(s1);

        // increase growth 3 days
        plots.get(0).increaseGrowth();
        plots.get(0).increaseGrowth();
        plots.get(0).increaseGrowth();
    }

    /* ============= buildEnv() ============= */
    // builds initial game objects
    public void buildEnv() {
        //initilaize vars
        makeCharacters();
        GardenPlot gplot1 = new GardenPlot();
        addPlot(gplot1); // everyone starts with one plot
    }


    /* ============= Start() ============= */
    // for playing the game
    public void Start(int days) {
        market.getInv();
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu();
        String name = menu.getName(this);
        this.player.setName(name);
        System.out.println("\n==> "+"Hello " + player.getName() + "! ");


        // for testing, populate friends array
        // Friends.add(Characters.get(0));
        // Friends.add(Characters.get(1));
        // Friends.add(Characters.get(2));

        int gPlot_idx = -2;
        int friend_idx = -1;

        // MENU KEY
        // menu1 = 0
        // selectGPlot = 1
        // ManageGardenMenu = 2
        // selectFriend = 3
        // Managefriends = 4
        // delegate job = 5
        // enterMarketValue = 6
        // check stats = 7
        int nextMenu = 0;
        while (day < days) {
            // Main Menu
            if (nextMenu == 0) {
                String navInd = menu.menu1();
                if (navInd.equals("1")) {
                    nextMenu = 1;
                }
                else if (navInd.equals("2")) {
                    nextMenu = 3;
                }
                else if (navInd.equals("3")) {
                    nextMenu = 6;
                }
                else if (navInd.equals("4")) {
                    displayInv();
                }
                else if (navInd.equals("5")) {
                    nextMenu = 7;
                }
                else if (navInd.equals("6")) {
                    nextMenu = 0;
                    newDay();
                }
            }

            // select gardens
            else if (nextMenu == 1) {
                String navInd = menu.selectGPlotMenu(plots);
                
                if (navInd.equals("1")) {
                    System.out.println("\n==> "+"Selected garden 1");
                    gPlot_idx = 0;
                    nextMenu = 2;
                }
                else if (navInd.equals("2")) {
                    gPlot_idx = 1;
                    nextMenu = 2;
                }
                else if (navInd.equals("3")) {
                    gPlot_idx = 2;
                    nextMenu = 2;
                }
                else if (navInd.equals("4")) {
                    gPlot_idx = 3;
                    nextMenu = 2;
                }
                else if (navInd.equals("5")) {
                    gPlot_idx = 4;
                    nextMenu = 2;
                }
                else if (navInd.equals("0")) {
                    nextMenu = 0;
                }
            }

            // Manage gardens
            else if (nextMenu == 2) {
                String navInd = menu.ManageGardenMenu();
                
                if (navInd.equals("1")) {
                    WaterPlot(gPlot_idx);
                    nextMenu = 2;
                }
                else if (navInd.equals("2")) {
                    PlantSeed(gPlot_idx);
                    nextMenu = 2;
                }
                else if (navInd.equals("3")) {
                    HarvestPlot(gPlot_idx);
                    nextMenu = 2;
                }
                else if (navInd.equals("4")) {
                    PullWeeds(gPlot_idx);
                    nextMenu = 2;
                }
                else if (navInd.equals("0")) {
                    nextMenu = 1;
                }

                if (player.tired()) {
                    nextMenu = 0;
                    newDay();
                }
            }

            // select friend
            else if (nextMenu == 3) {
                if (Friends.size() <= 0) {
                    System.out.println("\n==> "+"You currently have no friends.");
                    nextMenu = 0;
                }
                else {
                    String navInd = menu.selectFriendMenu(Friends);
                    if (navInd.equals("1")) {
                        friend_idx = 0;
                        nextMenu = 4;
                    }
                    else if (navInd.equals("2")) {
                        friend_idx = 1;
                        nextMenu = 4;
                    }
                    else if (navInd.equals("3")) {
                        friend_idx = 2;
                        nextMenu = 4;
                    }
                    else if (navInd.equals("4")) {
                        friend_idx = 3;
                        nextMenu = 4;
                    }
                    else if (navInd.equals("5")) {
                        friend_idx = 4;
                        nextMenu = 4;
                    }
                    else if (navInd.equals("0")) {
                        nextMenu = 0;
                    }
                }
                
            }

            // Manage friend 
            else if (nextMenu == 4) {
                String navInd = menu.ManageFriendsMenu(Friends.get(friend_idx));
                if (navInd.equals("1")) {
                    Friends.get(friend_idx).interact();
                }
                else if (navInd.equals("2")) {
                    nextMenu = 5;
                }
                else if (navInd.equals("0")) {
                    friend_idx = 2;
                    nextMenu = 3;
                }  
            }

            // Delegate job
            else if (nextMenu == 5) {
                String navInd = menu.DelegateJobMenu(Friends.get(friend_idx));
                Friend f = Friends.get(friend_idx);
                Random rand = new Random();
                if (navInd.equals("1")) {
                    System.out.println("\n==> "+f.getName() + " will water a random plot");
                    int idx = rand.nextInt(plots.size());
                    delegateTask(f, "water", idx);
                }
                else if (navInd.equals("2")) {
                    System.out.println("\n==> "+f.getName() + " will plant seeds in a random plot");
                    int idx = rand.nextInt(plots.size());
                    delegateTask(f, "plant", idx);
                }
                else if (navInd.equals("3")) {
                    System.out.println("\n==> "+f.getName() + " will harvest a random plot");
                    int idx = rand.nextInt(plots.size());
                    delegateTask(f, "harvest", idx);
                }
                else if (navInd.equals("4")) {
                    System.out.println("\n==> "+f.getName() + " will pull weeds from a random plot.");
                    int idx = rand.nextInt(plots.size());
                    delegateTask(f, "weed", idx);
                }
                else if (navInd.equals("5")) {
                    System.out.println("\n==> "+f.getName() + " will raise funds.");
                    delegateTask(f, "fundraise", 1);
                }
                else if (navInd.equals("0")) {
                    nextMenu = 4;
                }

                if (player.tired()) {
                    nextMenu = 0;
                    newDay();
                }
            }

            // enterMarketmenu
            else if (nextMenu == 6) {
                String navInd = menu.enterMarketMenu();
                Random rand = new Random();
                if (navInd.equals("1")) {
                    market.displaySeeds();
                    int seedToBuy = scanner.nextInt();
                    market.buySeed(this, seedToBuy);
                    player.decre_Energy(1);
                }
                else if (navInd.equals("2")) {
                    market.displayTools();
                    int toolToBuy = scanner.nextInt();
                    market.buyTool(this, (toolToBuy-1));
                    player.decre_Energy(1);
                }
                else if (navInd.equals("3")) {
                    market.buyPlot(this);
                    player.decre_Energy(1);
                }
                else if (navInd.equals("4")) {
                    int produce_idx = rand.nextInt(produce.size());
                    market.sellProduce(this, produce.get(produce_idx));
                    player.decre_Energy(1);
                }
                else if (navInd.equals("5")) {
                    int seed_idx = rand.nextInt(seeds.size());
                    market.sellSeed(this, seeds.get(seed_idx));
                    player.decre_Energy(1);
                }
                else if (navInd.equals("6")) {
                    Fundraise();
                }
                else if (navInd.equals("0")) {
                    nextMenu = 0;
                }

                if (player.tired()) {
                    nextMenu = 0;
                    newDay();
                }
            }

            else if (nextMenu == 7) {
                menu.checkStats(this);
                nextMenu = 0;
            }
        }
        // calculate the player's score
        getScore();
    }
}
