package simulationPackage;

import itemPackage.*;
import employeePackage.*;
import observerPackage.*;
import java.io.*;
import java.io.ObjectInputStream.GetField;
import java.util.*;
import java.text.SimpleDateFormat;

// Cohesion: the Simulation class has low cohesion because it is unfocused (has many methods)
public class Simulation{

    Clerk clerks[];  //List of clerks
    Trainer trainers[]; //List of trainers
    int day; //Current day (starts at 0)

    // NORTHSIDE VARIABLES
    Double registerN; // Cash balance of the register
    ArrayList<Pet> healthyPetsN = new ArrayList<Pet>(); //List of all healthy animals
    ArrayList<Pet> sickPetsN = new ArrayList<Pet>(); //List of all unhealthy animals
    Double bankDebtN = 0.0; //Cash debt to the bank
    ArrayList<Item> soldItemsN = new ArrayList<Item>();
    ArrayList<Item> deliveredItemsN = new ArrayList<Item>();//Items that have been devlivered to the store
    int newItemsN = 0; //keeps track of all new items 
    int itemsOrderedN = 0; // keep strack of how many items have been ordered
    ArrayList<Integer> deliveryScheduleN = new ArrayList<Integer>(); //There is a common index between deliveredItems and deliverySchedule
                                     // The value of delivery schdule corresponds to the delivery day of the item at the same index in delivered Items
                                     // ie. If index 3 in deliverySchedule has the value 4, then the item of index 3 in deliveredItems should be delivered on day 4.
    ArrayList<Item> nonAnimalItemsN = new ArrayList<Item>(); //List of all items in the shop that are not animals
    int customersN = 0; // number of customers that have visited the store

    // SAME VARIABLES FOR SOUTHSIDE
    Double registerS;
    ArrayList<Pet> healthyPetsS = new ArrayList<Pet>();
    ArrayList<Pet> sickPetsS = new ArrayList<Pet>();
    Double bankDebtS = 0.0;
    ArrayList<Item> soldItemsS = new ArrayList<Item>();
    ArrayList<Item> deliveredItemsS = new ArrayList<Item>();
    int newItemsS = 0; 
    int itemsOrderedS = 0;
    ArrayList<Integer> deliveryScheduleS = new ArrayList<Integer>(); 
    ArrayList<Item> nonAnimalItemsS = new ArrayList<Item>();
    int customersS = 0;

    
    //NOTE: Inventroy is considered to be the combined list of nonAnimalItems and HealthyAnimals.
    //Sick animals are not included in the inventory.

    //The following lists are to generate items:
    ArrayList<String> names20 = new ArrayList<String>(Arrays.asList("Albus", "Stan", "Prince Schnookums", "Freddie", "Sam", "Bailey", "Sooty", "Whiskey", "Ted", "Pheonix",
            "Misty", "Bella", "Crumble", "Olive", "Cleo", "Coco", "Honey", "Pickle", "Rosie", "Nala"));
    ArrayList<String> sizes = new ArrayList<String>(Arrays.asList("small", "medium", "large", "very large"));
    ArrayList<String> dogBreed = new ArrayList<String>(Arrays.asList("Pitbull", "Lab", "Poodle"));
    ArrayList<String> dogColor = new ArrayList<String>(Arrays.asList("Brown", "Tan", "Spotted"));
    ArrayList<String> catBreed = new ArrayList<String>(Arrays.asList("Tabby", "Russian Blue", "Orange"));
    ArrayList<String> birdBreed = new ArrayList<String>(Arrays.asList("Parrot", "Pigeon", "Cocatrice"));
    ArrayList<String> animals = new ArrayList<String>(Arrays.asList("Bird", "Dog", "Cat"));
    ArrayList<String> foodType = new ArrayList<String>(Arrays.asList("Dry Food", "Wet Food", "Treats"));
   


    //Basic Constructor: pass in a set of 4 pre-instantiated employees. (2 clerks and 2 trainers)
    public Simulation(Employee [] clerks, Employee [] trainers){
        this.clerks = (Clerk[])clerks;
        this.trainers = (Trainer[])trainers;
        day = 0;

        registerN = 0.0;
        deliveredItemsN = new ArrayList<Item>();
        deliveryScheduleN = new ArrayList<Integer>();

        registerS = 0.0;
        deliveredItemsS = new ArrayList<Item>();
        deliveryScheduleS = new ArrayList<Integer>();
    }

    /* ================================ ALL OBERSERVERS AND OBSERVABLES ================================ */

    // inventory added items observation
    inventoryCount invCount_ov = new inventoryCount();
    inventoryObs invCount_obs = new inventoryObs();

    // inventory observations
    inventoryValue in_Val = new inventoryValue();
    inventoryValObs in_val_obs = new inventoryValObs();
    itemsOV items_ov = new itemsOV();
    itemsObs items_obs = new itemsObs();

    // register value observation
    registerOV regOv = new registerOV();
    registerObs regObs = new registerObs();

    // bank observer
    bankOV bank_ov = new bankOV();
    bankObs bank_obs = new bankObs();

    // orders observer
    orderOV order_ov = new orderOV();
    orderObs order_obs = new orderObs(); 

    // escape observer
    escapeOV escape_ov = new escapeOV();
    escapeObs escape_obs = new escapeObs();

    // cutomer visit observer
    visitsOV visit_ov = new visitsOV();
    visitsObs visit_obs = new visitsObs();

    // arrive observer
    arriveOV arrive_ov = new arriveOV();
    arriveObs arrive_obs = new arriveObs();

    // leave observer
    leaveOV leave_ov = new leaveOV();
    leaveObs leave_obs = new leaveObs();
    /* ======================================== General Helpers & Generators ======================================== */

    //This is a helper fuction to generate animals:
    //Given an ArrayList, it will randomly choose an element.
    //Index of element in returned
    public int pickRandFromList(int size){
        Random rand = new Random();
        return rand.nextInt(size);
    }

    public int priceGen(int min, int max){
        Random rand = new Random();
        return (rand.nextInt(max - min) + min);
    }

    // helper to help add inventory total value
    // excludes sick animals
    public double getInventoryTotal(String NS){
        double total = 0.0;
        ArrayList<Pet> healthyPets = new ArrayList<>();
        ArrayList<Item> nonAnimalItems = new ArrayList<>();

        if (NS == "N") {
            healthyPets = healthyPetsN;
            nonAnimalItems = nonAnimalItemsN;
        }
        else if (NS == "S") {
            healthyPets = healthyPetsS;
            nonAnimalItems = nonAnimalItemsS;
        }
        else {
            System.out.println("getInventoryTotal() ERROR: char must be N or S. returing -1.0...");
            return -1.0;
        }

        //iterate through healthy animals
        for (int i = 0; i < healthyPets.size(); i++) {
            total += healthyPets.get(i).getPurchasePrice(); // increment total price by pruchase price of that item
        }

        //iterate through non-animal items
        for (int j = 0; j < nonAnimalItems.size(); j++) {
            total += nonAnimalItems.get(j).getPurchasePrice(); // increment total price by pruchase price of that item
        }

        return total;
    }

    // helper to help add sale total value
    public double getSaleTotal(String NS){
        double saleTot = 0.0;
        ArrayList<Item> soldItems = new ArrayList<>();

        if (NS == "N") {
            soldItems = soldItemsN;
        }
        else if (NS == "S") {
            soldItems = soldItemsS;
        }
        else {
            System.out.println("getSaleTotal() ERROR: char must be N or S. returing -1.0...");
            return -1.0;
        }

        //iterate through soldItem collection
        for (int i = 0; i < soldItems.size(); i++){
            saleTot += soldItems.get(i).getSalePrice();
        }

        return saleTot;
    }

    // helper to print all sales, incuding daySold and salePrice
    public void printSales(String NS){
        ArrayList<Item> soldItems = new ArrayList<>();

        if (NS == "N") {
            soldItems = soldItemsN;
            System.out.println("\nAll sales made at Northside Branch: ");
        }
        else if (NS == "S") {
            soldItems = soldItemsS;
            System.out.println("\nAll sales made at Southside Branch: ");
        }
        else {
            System.out.println("printSales() ERROR: char must be N or S.");
            return;
        }


        //iterate through soldItem collection
        for (int i = 0; i < soldItems.size(); i++){
            String n = soldItems.get(i).getName();
            int d = soldItems.get(i).getDayArrived();
            double p = soldItems.get(i).getSalePrice();
            System.out.println("=> " + n + " was sold on day " + d + " for $" + p);
        }
    }

    // helper to print sick animals
    public void printSick(String NS){
        ArrayList<Pet> sickPets = new ArrayList<>();

        if (NS == "N") {
            sickPets = sickPetsN;
            System.out.println("\nAll sick pets left at Northside Branch: ");
        }
        else if (NS == "S") {
            sickPets = sickPetsS;
            System.out.println("\nAllsick pets left at Southside Branch: ");
        }
        else {
            System.out.println("printSick() ERROR: char must be N or S.");
            return;
        }


        //iterate through soldItem collection
        for (int i = 0; i < sickPets.size(); i++){
            String n = sickPets.get(i).getName();
            String b = sickPets.get(i).getBreed();
            System.out.println("=> " + n + " the " + b);
        }
    }

    // helper to print remaining inventory
    public void printRemains(String NS){
        ArrayList<Pet> healthyPets = new ArrayList<>();
        ArrayList<Item> nonAnimalItems = new ArrayList<>();
        int itemNum = 0;

        if (NS == "N") {
            healthyPets = healthyPetsN;
            nonAnimalItems = nonAnimalItemsN;
        }
        else if (NS == "S") {
            healthyPets = healthyPetsS;
            nonAnimalItems = nonAnimalItemsS;
        }
        else {
            System.out.println("printRemain() ERROR: char must be N or S.");
        }

        System.out.println("=> Here are all the remaining items in stock: ");


        //iterate through healthy animals
        for (int i = 0; i < healthyPets.size(); i++) {
            System.out.println("(" + itemNum+ ") "+healthyPets.get(i).getName() + " the " + healthyPets.get(i).getBreed());
            itemNum++;
        }

        //iterate through non-animal items
        for (int j = 0; j < nonAnimalItems.size(); j++) {
            System.out.println("(" + itemNum+ ") "+nonAnimalItems.get(j).getName());
            itemNum++;
        }

        return;
    }
//
//    // Abstraction: genDog is called multiple times in program
//    public Dog genDog(int today){
//        ArrayList<Boolean> boolist = new ArrayList<>(Arrays.asList(true,false));
//        String size = sizes.get(pickRandFromList(sizes.size()));
//        String color = dogColor.get(pickRandFromList(dogColor.size()));
//        boolean bool1 = boolist.get(pickRandFromList(boolist.size()));
//        boolean bool2 = boolist.get(pickRandFromList(boolist.size()));
//        boolean bool3 = boolist.get(pickRandFromList(boolist.size()));
//        String name = names20.get(pickRandFromList(names20.size()));
//        String breed = dogBreed.get(pickRandFromList(dogBreed.size()));
//        int age = priceGen(1,14);
//        Dog d1 = new Dog(size, color, bool1, bool2);
//        d1.setItemInfo(name, priceGen(0,300)*1.0, priceGen(300,500)*1.0, today);
//        d1.setPetInfo(breed,age,bool3);
//        return d1;
//    }
//
//    public Cat genCat(int today){
//        ArrayList<Boolean> boolist = new ArrayList<>(Arrays.asList(true,false));
//        String size = sizes.get(pickRandFromList(sizes.size()));
//        boolean bool1 = boolist.get(pickRandFromList(boolist.size()));
//        boolean bool2 = boolist.get(pickRandFromList(boolist.size()));
//        boolean bool3 = boolist.get(pickRandFromList(boolist.size()));
//        String name = names20.get(pickRandFromList(names20.size()));
//        String breed = catBreed.get(pickRandFromList(catBreed.size()));
//        int age = priceGen(1,14);
//        Cat c1 = new Cat(size, bool1, bool2);
//        c1.setItemInfo(name, priceGen(0,300)*1.0, priceGen(300,500)*1.0, today);
//        c1.setPetInfo(breed,age,bool3);
//        return c1;
//    }
//
//    public Bird genBird(int today){
//        ArrayList<Boolean> boolist = new ArrayList<>(Arrays.asList(true,false));
//        String size = sizes.get(pickRandFromList(sizes.size()));
//        boolean bool1 = boolist.get(pickRandFromList(boolist.size()));
//        boolean bool2 = boolist.get(pickRandFromList(boolist.size()));
//        boolean bool3 = boolist.get(pickRandFromList(boolist.size()));
//        boolean bool4 = boolist.get(pickRandFromList(boolist.size()));
//        String name = names20.get(pickRandFromList(names20.size()));
//        String breed = birdBreed.get(pickRandFromList(birdBreed.size()));
//        int age = priceGen(1,14);
//        Bird b1 = new Bird(size, bool1, bool2, bool3);
//        b1.setItemInfo(name, priceGen(0,300)*1.0, priceGen(300,500)*1.0, today);
//        b1.setPetInfo(breed,age,bool4);
//        return b1;
//    }
//
//    public CatLitter genCatLitter(int today){
//        String size = sizes.get(pickRandFromList(sizes.size()));
//        CatLitter cl = new CatLitter(size);
//        cl.setItemInfo("FNPS Cat Litter (TM)", priceGen(0,20)*1.0, priceGen(20,40)*1.0, today);
//        return cl;
//    }
//
//    public Toy genToy(int today){
//        String animal = animals.get(pickRandFromList(animals.size()));
//        Toy toy = new Toy(animal);
//        toy.setItemInfo("FNPS " + animal + " Toy (TM)", priceGen(0,20)*1.0, priceGen(20,40)*1.0, today);
//        return toy;
//    }
//
//    public food genFood(int today){
//        String animal = animals.get(pickRandFromList(animals.size()));
//        String foodtype = foodType.get(pickRandFromList(foodType.size()));
//        food cl = new food(animal + " " + foodtype + " food",animal, foodtype );
//        cl.setItemInfo("FNPS " +  animal + " " + foodtype + " (TM)", priceGen(0,20)*1.0, priceGen(20,40)*1.0, today);
//        return cl;
//    }
//
//    public Leash genLeash(int today){
//        String animal = animals.get(pickRandFromList(animals.size()));
//        Leash lsh = new Leash(animal);
//        lsh.setItemInfo("FNPS " + animal + " Leash (TM)", priceGen(0,20)*1.0, priceGen(20,40)*1.0, today);
//        return lsh;
//    }


    //This function is largely for testing!!!
    //Add the healthy pets and sick pets directly to the simulation
    public void addPets(ArrayList<Pet> hPets, ArrayList<Pet> sPets, String NS){
        if (NS == "N") {
            healthyPetsN = hPets;
            sickPetsN = sPets;
        }
        else if (NS == "S") {
            healthyPetsS = hPets;
            sickPetsS = sPets;
        }
        else {
            System.out.println("addPets() ERROR: char must be N or S.");
        }

    }

    /* ======================================== Getters ======================================== */

    public ArrayList<Item> getDeliveredItems(String NS){
        if (NS == "N") {
            return deliveredItemsN;
        }
        else if (NS == "S") {
            return deliveredItemsS;
        }
        else {
            System.out.println("getDeliveredItems() ERROR: char must be N or S. returning null...");
            return null;
        }
    }

    public ArrayList<Integer> getDeliverySchedule(String NS){
        if (NS == "N") {
            return deliveryScheduleN;
        }
        else if (NS == "S") {
            return deliveryScheduleS;
        }
        else {
            System.out.println("getDeliveryScehdule() ERROR: char must be N or S. returning null...");
            return null;
        }
    }

    //This function will go through a list of employees and check to see if they
    //can work for the coming day. This factors in sickness, and
    //consecutive days worked.
    //This will return an eligible employee.
    public Employee getEligibleEmp(Employee[] emps) {
        Random rand = new Random();
        Employee eligible = null;
        ArrayList<Employee> unavailble = new ArrayList<Employee>();
        while (eligible == null && unavailble.size() != emps.length){
            int temp = pickRandFromList(emps.length);
            //System.out.println(temp);
            Employee curEmp = emps[temp];

            if (!curEmp.isSick() && curEmp.getDaysWorked() < 3 && !unavailble.contains(curEmp) ) {
                curEmp.incrDaysWorked();
                //System.out.println(curEmp.getName() + " has worked " + curEmp.getDaysWorked() + " days.");
                eligible = curEmp;
            }else{
                if(!unavailble.contains(curEmp)) {
                    unavailble.add(curEmp);
                }
            }
            if(unavailble.size() == emps.length){
                System.out.println("Oh not! All of the clerks or all of the trainers are unavalible. Forcing one to come in anyways...");
                eligible = emps[pickRandFromList(emps.length)];
            }
        }
        return eligible;
    }



    /* ======================================== 1. ArrivedAtStore() V3 ======================================== */
    //Get the employees for the day
    //Choose 1 random clerk and 1 random trainer.
    //If either have worked for 3 days in a row already, take the other
    //employee of the same type.
    //If an employee is sick, choose another employee
    //Keeps track of days worked automatically.
    //Now assigns the employees across the two locations.

    //Return two lists of the 2 employees to work that day.
    //The first list of employees is for the Northside, the second list is for the south side.
    //The first employee of one of the return arrays is the clerk, the second employee is the trainer.

    public ArrayList<Employee[]> ArriveAtStore(){
        Employee[] empsN = new Employee[2]; //northside employees
        Employee[] empsS = new Employee[2]; // southside employees
        empsN[0] = getEligibleEmp(clerks);  //Get the Northside employees from the full pool of the employees
        empsN[1] = getEligibleEmp(trainers);
        Employee[] tempEmpsClerk = new Employee[clerks.length-1];
        Employee[] tempEmpsTrainers = new Employee[trainers.length-1];
        if(trainers.length != clerks.length){
            System.out.println(">>>>>>POTENTIAL ERROR!!!!! clerks and trainers have dif sized list. May screw the logic of ArriveAtStore");
        }
        int next1 = 0;
        int next2 = 0;
        for(int i = 0; i < clerks.length; i ++){    //create arrays of the clerks and the trainers that arent in the northside today
            if(clerks[i] != empsN[0]){
                tempEmpsClerk[next1] = clerks[i];
                next1++;

            }
            if(trainers[i] != empsN[1]){
                tempEmpsTrainers[next2] = trainers[i];
                next2++;
            }
        }

        empsS[0] = getEligibleEmp(tempEmpsClerk);   //Get employees from the reduced pool of the trainers and clerks.
        empsS[1] = getEligibleEmp(tempEmpsTrainers);
        for(int i = 0; i < clerks.length; i++){
            if(clerks[i] != empsS[0] && clerks[i] != empsN[0]){
                clerks[i].resetDaysWorked();
            }
            if(trainers[i] != empsS[1] && trainers[i] != empsN[1]){
                trainers[i].resetDaysWorked();
            }
        }

        System.out.println(empsN[0].getName() + " the Clerk arrives on Day " + day + " at the Northside branch");
        System.out.println(empsN[1].getName() + " the Trainer arrives on Day " + day + " at the Northside branch");
        System.out.println(empsS[0].getName() + " the Clerk arrives on Day " + day + " at the Southside branch");
        System.out.println(empsS[1].getName() + " the Trainer arrives on Day " + day + " at the Southside branch");

        // Do observer things...
        arrive_obs.setStore("Northside branch");
        arrive_obs.setEmp(empsN[0], "clerk");
        arrive_ov.setEmp(empsN[0]);

        arrive_obs.setEmp(empsN[1], "trainer");
        arrive_ov.setEmp(empsN[1]);

        arrive_obs.setStore("Southside branch");
        arrive_obs.setEmp(empsS[0], "clerk");
        arrive_ov.setEmp(empsS[0]);

        arrive_obs.setEmp(empsS[1], "trainer");
        arrive_ov.setEmp(empsS[1]);

        ArrayList<Employee[]> allEmps = new ArrayList<Employee[]>(Arrays.asList(empsN, empsS));  //Return both northside and southside employees
        return allEmps;
    }


    /* ======================================== 2. ProcessDeliveries() ======================================== */
    // This function will iterate through deliveredItems and deliverySchedule to determine new items that have arrived
    // If the deliverySchedule == today, then annouce
    // Recall that the value of delivery schdule corresponds to the delivery day of the item at the same index in delivered Items
        // ie. If index 3 in deliverySchedule has the value 4, then the item of index 3 in deliveredItems should be delivered on day 4.
    public void ProcessDeliveries(String NS){

        //Item arrivedItem = new Item(); // Polymorphism: arrivedItem can be treated as either a pet or an item
        int today = this.day;

        if (NS == "N") {
            int sz = deliveryScheduleN.size(); // Identity: although sz is derived from deliverySchedule.size(), it is completely unique and therefore has its own identity
            boolean isDeliveries = false; // tells us if any new items have arrived today

            for (int i = 0; i < sz; i++) {
                if (deliveryScheduleN.get(i) == today) { //check if the item's arrival day is today
                    //arrivedItem = deliveredItems.get(i);
                    isDeliveries = true;
                    newItemsN++;
                    if (deliveredItemsN.get(i) instanceof Pet) {
                        Pet temp = (Pet) deliveredItemsN.get(i);
                        healthyPetsN.add(temp);
                    }
                    else {
                        nonAnimalItemsN.add(deliveredItemsN.get(i));
                    }
                    System.out.println(deliveredItemsN.get(i).getName() + "has arrived at Northside location.");
                }
            }
    
            // Update observer
            invCount_obs.setStore("Northside");
            if (isDeliveries == true ) {
                invCount_ov.setCount(newItemsN);
            }
            
        }
        else if (NS == "S") {
            int sz = deliveryScheduleS.size(); // Identity: although sz is derived from deliverySchedule.size(), it is completely unique and therefore has its own identity
            boolean isDeliveries = false; // tells us if any new items have arrived today

            for (int i = 0; i < sz; i++) {
                if (deliveryScheduleS.get(i) == today) { //check if the item's arrival day is today
                    //arrivedItem = deliveredItems.get(i);
                    isDeliveries = true;
                    newItemsS++;
                    if (deliveredItemsS.get(i) instanceof Pet) {
                        Pet temp = (Pet) deliveredItemsS.get(i);
                        healthyPetsS.add(temp);
                    }
                    else {
                        nonAnimalItemsS.add(deliveredItemsS.get(i));
                    }
                    System.out.println(deliveredItemsS.get(i).getName() + "has arrived at Southside location.");
                }
            }
    
            // Update observer
            invCount_obs.setStore("Southside");
            if (isDeliveries == true ) {
                invCount_ov.setCount(newItemsS);
            }
        }
        else {
            System.out.println("ProcessDeliveries() ERROR: char must be N or S.");
        }

    }


    /* ======================================== 3. FeedAnimals() ======================================== */
    //This function feeds all the resident animals.
    //The sick and healthy animals are fed seperately.
    //There is a 5% chance healhty animals fall ill, and a 25% chance ill animals recover
    //When this happens, their 'healthy' data will switch acordingly, and they will be
    //moved to the appropriate list of animals (healthy vs sick)
    public void FeedAnimals(String NS){
        System.out.println("Today's trainer is now feeding the resident animals.");
        Random rand = new Random();
        ArrayList<Pet> toSick = new ArrayList<Pet>();  //Lists for switching animals
        ArrayList<Pet> toHealthy = new ArrayList<Pet>();
        
        if (NS == "N") {
            int hPetsLength = healthyPetsN.size();
            int sPetsLength = sickPetsN.size();

            for(int i = 0; i < hPetsLength; i++){
                if(rand.nextInt(100) <= 5) { //healthy animal falls ill
                    System.out.println("While feeding, " + healthyPetsN.get(i).getName() + " the " + 
                        healthyPetsN.get(i).getBreed() + " has fallen ill...");

                    healthyPetsN.get(i).toSick();  //Move to sick list
                    toSick.add(healthyPetsN.get(i));
                    healthyPetsN.remove(i);
                    i--;  //Fix indexing of altered list
                    hPetsLength--;
                }
            }

            for(int i = 0; i < sPetsLength; i++){
                if(rand.nextInt(100) <= 25) { //sick animal gets better
                    System.out.println("While feeding, " + sickPetsN.get(i).getName() + " the " + 
                        sickPetsN.get(i).getBreed() + " has become healthy!");

                    sickPetsN.get(i).toHealthy(); //Move animal to healthy list
                    toHealthy.add(sickPetsN.get(i));
                    sickPetsN.remove(i);
                    i--; //Fix index of altered list
                    sPetsLength--;
                }
            }

            healthyPetsN.addAll(toHealthy);
            sickPetsN.addAll(toSick);
        }
        else if (NS == "S") {
            int hPetsLength = healthyPetsS.size();
            int sPetsLength = sickPetsS.size();

            for(int i = 0; i < hPetsLength; i++){
                if(rand.nextInt(100) <= 5) { //healthy animal falls ill
                    System.out.println("While feeding, " + healthyPetsS.get(i).getName() + " the " + 
                        healthyPetsS.get(i).getBreed() + " has fallen ill...");

                    healthyPetsS.get(i).toSick();  //Move to sick list
                    toSick.add(healthyPetsS.get(i));
                    healthyPetsS.remove(i);
                    i--;  //Fix indexing of altered list
                    hPetsLength--;
                }
            }

            for(int i = 0; i < sPetsLength; i++){
                if(rand.nextInt(100) <= 25) { //sick animal gets better
                    System.out.println("While feeding, " + sickPetsS.get(i).getName() + " the " + 
                        sickPetsS.get(i).getBreed() + " has become healthy!");

                    sickPetsS.get(i).toHealthy(); //Move animal to healthy list
                    toHealthy.add(sickPetsS.get(i));
                    sickPetsS.remove(i);
                    i--; //Fix index of altered list
                    sPetsLength--;
                }
            }

            healthyPetsS.addAll(toHealthy);
            sickPetsS.addAll(toSick);
        }
        else {
            System.out.println("FeedTheAnimals() ERROR: char must be N or S.");
        }
 
    }


    /* ======================================== 4. CheckRegister() ======================================== */
    // The clerk counts the resgister
    // They annouce the total in the register
    // If funds or insufficient (i.e. less that $200), the clerk wil go to the bank
    public void CheckRegister(Employee[] emps, String NS){
        
        if ( NS == "N") {
            System.out.println(emps[0].getName() + " the clerk is counting the register...");
            System.out.println("There's currently $" + registerN + " in the register.");

            regObs.setStore("Northside");
            regOv.setReg(registerN);
        
            if (this.registerN < 200.0) {
                GoToBank(emps, NS);
            }
        }
        else if (NS == "S") {
            System.out.println(emps[0].getName() + " the clerk is counting the register...");
            System.out.println("There's currently $" + registerS + " in the register.");

            regObs.setStore("Southside");
            regOv.setReg(registerS);
        
            if (this.registerS < 200.0) {
                GoToBank(emps, NS);
            }
        }
        else {
            System.out.println("CheckRegister() ERROR: char must be N or S.");
        }

        
    }

    /* ======================================== 5. GoToBank() ======================================== */
    //This is a helper function for GoToBank.
    //If this function returns true, the function GoToBank should be called
    // public boolean regBelow200(){
    //     if (register < 200.0){
    //         return true;
    //     }else{
    //         return false;
    //     }
    // }


    //Pass in the currently active employees
    //This function will withdraw 1000 form the bank, and add it to the register
    //Input the current employees for the day
    public void GoToBank(Employee[] emps, String NS){
        if ( NS == "N") {
            while (registerN <= 200.0) {
                System.out.println("The register's balance is " + registerN+ " which is below $200.");
                registerN += 1000.0;
                System.out.println(emps[0].getName() + " the clerk has gone to the bank to aquire a small loan of $1000.");
                
                bankDebtN += 1000.0;
                System.out.println("The debt to the bank is now " + bankDebtN);        
                
                if (registerN >= 200.0) {
                    break;
                }
            }     
        // Do observer things
        bank_obs.setStore("Northside");
        bank_ov.setVal(registerN);
        }
        else if (NS == "S") {
            while (registerS <= 200.0) {
                
                System.out.println("The register's balance is " + registerS+ " which is below $200.");
                registerS += 1000.0;

                System.out.println(emps[0].getName() + " the clerk has gone to the bank to aquire a small loan of $1000.");
                
                bankDebtS += 1000.0;
                System.out.println("The debt to the bank is now " + bankDebtS);

                if (registerS >= 200.0) {
                    break;
                }
            }
            // Do observer things
            bank_obs.setStore("Northside");
            bank_ov.setVal(registerS);
        }
        else {
            System.out.println("GoToBank() ERROR: char must be N or S.");
        }
        
        
    }


    /* ======================================== 6. DoInventory() ======================================== */
    // DoInventory : I was considering sub classes to be dog, cat, bird, leash, toy, cat litter, and food
    // Pass a list of missing items to the following function (Place an order).
    // The list of missing items should be an Arraylist of strings, ie. ["dog", "cat", "bird", "catlitter", etc...]
    // (no caps no spaces)

    public ArrayList<String> DoInventory(String NS) {
        System.out.println("The clerk is doing inventory.");

        ArrayList<Pet> healthyPets = new ArrayList<>();
        ArrayList<Item> nonAnimalItems = new ArrayList<>();

        if (NS == "N"){
            in_val_obs.setStore("Northside");
            items_obs.setStore("Northside");
            healthyPets = healthyPetsN;
            nonAnimalItems = nonAnimalItemsN;
        }
        else if (NS == "S") {
            in_val_obs.setStore("Southside");
            items_obs.setStore("Southside");
            healthyPets = healthyPetsS;
            nonAnimalItems = nonAnimalItemsS;
        }
        else {
            System.out.println("DoInventory() ERROR: char must be N or S.");
        }

        //calculate inventory value
        double valueInventory = getInventoryTotal(NS);
        int itemTot = healthyPets.size() + nonAnimalItems.size();

        // observer things
        in_Val.setVal(valueInventory);
        items_ov.setItemNum(itemTot);

        double total = 0.0;
        int dogCnt = 0;
        int catCnt = 0;
        int birdCnt = 0;
        int snakeCnt = 0;
        int ferretCnt = 0;
        int leashCnt = 0;
        int foodCnt = 0;
        int litterCnt = 0;
        int toyCnt = 0;
        int treatsCnt = 0;

        //Item curr = new Item();

        //iterate through healthy animals
        for (int i = 0; i < healthyPets.size(); i++) {
            total += healthyPets.get(i).getPurchasePrice(); // increment total price by pruchase price of that item

            // check what type of animal, increment accordingly
            if (healthyPets.get(i) instanceof Dog) {
                dogCnt++;
            }

            if (healthyPets.get(i) instanceof Cat) {
                catCnt++;
            }

            if (healthyPets.get(i) instanceof Bird) {
                birdCnt++;
            }

            if (healthyPets.get(i) instanceof Snake) {
                snakeCnt++;
            }

            if (healthyPets.get(i) instanceof Ferret) {
                ferretCnt++;
            }
        }

        //iterate through non-animal items
        for (int i = 0; i < nonAnimalItems.size(); i++) {
            total += nonAnimalItems.get(i).getPurchasePrice(); // increment total price by pruchase price of that item

            // check to see what kind of non-animal item, incrememnt accordingly
            if (nonAnimalItems.get(i) instanceof Leash) {
                leashCnt++;
            }
            
            if (nonAnimalItems.get(i) instanceof food) {
                foodCnt++;
            }

            if (nonAnimalItems.get(i) instanceof CatLitter) {
                litterCnt++;
            }

            if (nonAnimalItems.get(i) instanceof Toy) {
                toyCnt++;
            }

            if (nonAnimalItems.get(i) instanceof Treats) {
                treatsCnt++;
            }

        }
        
        // ArrayList to hold all missing items 
        ArrayList<String> missingItems = new ArrayList<>();
        
        // if there are no dogs in the store, add "dog" to the list
        // do for every item
        if (dogCnt <= 0){ 
            missingItems.add("dog");
        }

        if (catCnt <= 0){
            missingItems.add("cat");
        }

        if (birdCnt <= 0){
            missingItems.add("bird");
        }

        if (snakeCnt <= 0){
            missingItems.add("snake");
        }

        if (ferretCnt <= 0){
            missingItems.add("ferret");
        }

        
        if (leashCnt <= 0){
            missingItems.add("leash");
        }
        
        if (foodCnt <= 0){
            missingItems.add("food");
        }

        if (litterCnt <= 0){
            missingItems.add("catlitter");
        }

        /* NO LONGER SELLING TOYS IN THE STORE */
        if (toyCnt <= 0){
            System.out.println("There are no more toys in the store!");
        }

        if (treatsCnt <= 0){
            missingItems.add("treats");
        }

        // if there are missing items, call place an order
        if (missingItems.size() > 0) {
            System.out.println("Oh no, there are missing items from the store! The clerk will place an order.");
        }

        // Annouce the total inventory value
        System.out.println("The current inventory's total value is: $" + total);
        return missingItems;
    }



    /* ======================================== 7. PlaceAnOrder() ======================================== */
    // Place an order
    // This function should take in String that is the type of the item that is missing.
    // the input string should follow the guidlines: no caps, no spaces

    public void PlaceAnOrder(ArrayList<String> missingItems, int dayNumber, String NS){
        ConcrAnimFact animFact = new ConcrAnimFact();
        ConcrNAnimFact itemFact = new ConcrNAnimFact();
        Double costOfItems = 0.0;
        boolean orderPlaced = false; // if an order has been placed, then true.
        Random rand = new Random();

        if (NS == "N") {
            if(missingItems.contains("dog")){ //If dog is in the missing items list,
                orderPlaced = true;

                for(int i = 0; i < 3; i++){
                    Dog d1 = (Dog)animFact.createItem("dog", dayNumber);  //Generate 3 dogs
                    costOfItems += d1.getPurchasePrice(); //Add their cost to the order total
                    deliveryScheduleN.add(rand.nextInt(3)+1 + dayNumber); //Add the day it will get here
                    deliveredItemsN.add(d1);  // add the item to the delivered items list
                    itemsOrderedN++;
                }
            }
    
            if(missingItems.contains("cat")){
                orderPlaced = true;

                for(int i = 0; i < 3; i++){
                    Cat d1 = (Cat)animFact.createItem("cat", dayNumber);
                    costOfItems += d1.getPurchasePrice();
                    deliveryScheduleN.add(rand.nextInt(3)+1+ dayNumber);
                    deliveredItemsN.add(d1);
                    itemsOrderedN++;
                }
            }
    
            if(missingItems.contains("bird")){
                orderPlaced = true;

                for(int i = 0; i < 3; i++){
                     Bird d1 = (Bird)animFact.createItem("bird", dayNumber);
                     costOfItems += d1.getPurchasePrice();
                     deliveryScheduleN.add(rand.nextInt(3)+1+ dayNumber);
                     deliveredItemsN.add(d1);
                     itemsOrderedN++;
                }
            }
    
            if(missingItems.contains("ferret")){
                orderPlaced = true;

                for(int i = 0; i < 3; i++){
                    Ferret d1 = (Ferret)animFact.createItem("ferret", dayNumber);
                    costOfItems += d1.getPurchasePrice();
                    deliveryScheduleN.add(rand.nextInt(3)+1+ dayNumber);
                    deliveredItemsN.add(d1);
                    itemsOrderedN++;
                }
            }
    
            if(missingItems.contains("snake")){
                orderPlaced = true;

                for(int i = 0; i < 3; i++){
                    Snake d1 = (Snake)animFact.createItem("snake", dayNumber);
                    costOfItems += d1.getPurchasePrice();
                    deliveryScheduleN.add(rand.nextInt(3)+1+ dayNumber);
                    deliveredItemsN.add(d1);
                    itemsOrderedN++;
                }
            }
    
            if(missingItems.contains("leash")){
                orderPlaced = true;

                for(int i = 0; i < 3; i++) {
                     Leash d1 = (Leash) itemFact.createItem("leash", dayNumber);
                     costOfItems += d1.getPurchasePrice();
                     deliveryScheduleN.add(rand.nextInt(3)+1+ dayNumber);
                     deliveredItemsN.add(d1);
                     itemsOrderedN++;
                }
            }
    
            if(missingItems.contains("food")){
                orderPlaced = true;

                for(int i = 0; i < 3; i++) {
                     food d1 = (food) itemFact.createItem("food", dayNumber);
                     costOfItems += d1.getPurchasePrice();
                     deliveryScheduleN.add(rand.nextInt(3)+1+ dayNumber);
                     deliveredItemsN.add(d1);
                     itemsOrderedN++;
                }
            }
    
            if(missingItems.contains("catlitter")){
                orderPlaced = true;

                for(int i = 0; i < 3; i++) {
                     CatLitter d1 = (CatLitter) itemFact.createItem("catLitter", dayNumber);
                     costOfItems += d1.getPurchasePrice();
                     deliveryScheduleN.add(rand.nextInt(3)+1+ dayNumber);
                     deliveredItemsN.add(d1);
                     itemsOrderedN++;
                }
            }
    
            if(missingItems.contains("treats")){
                orderPlaced = true;

                for(int i = 0; i < 3; i++) {
                    Treats d1 = (Treats) itemFact.createItem("treats", dayNumber);
                    costOfItems += d1.getPurchasePrice();
                    deliveryScheduleN.add(rand.nextInt(3)+1+ dayNumber);
                    deliveredItemsN.add(d1);
                    itemsOrderedN++;
                }
            }

            /* NO LONGER SELLING TOYS IN THE STORE */
            //  if(missingItems.contains("toy")){
            //      for(int i = 0; i < 3; i++) {
            //          Toy d1 = genToy(dayNumber);
            //          costOfItems += d1.getPurchasePrice();
            //          deliverySchedule.add(rand.nextInt(3)+1+ dayNumber);
            //          deliveredItems.add(d1);
            //      }
            //  }
                
            if (orderPlaced == true) {
                order_obs.setStore("Northside");
                order_ov.setVal(itemsOrderedN);
                registerN -= costOfItems; //subtract the cost of the order from the register
            }
            orderPlaced = false;
        }
        else if (NS == "S"){
            if(missingItems.contains("dog")){ //If dog is in the missing items list,
                orderPlaced = true;

                for(int i = 0; i < 3; i++){
                    Dog d1 = (Dog)animFact.createItem("dog", dayNumber);  //Generate 3 dogs
                    costOfItems += d1.getPurchasePrice(); //Add their cost to the order total
                    deliveryScheduleS.add(rand.nextInt(3)+1 + dayNumber); //Add the day it will get here
                    deliveredItemsS.add(d1);  // add the item to the delivered items list
                    itemsOrderedS++;
                }
            }
    
            if(missingItems.contains("cat")){
                orderPlaced = true;

                for(int i = 0; i < 3; i++){
                    Cat d1 = (Cat)animFact.createItem("cat", dayNumber);
                    costOfItems += d1.getPurchasePrice();
                    deliveryScheduleS.add(rand.nextInt(3)+1+ dayNumber);
                    deliveredItemsS.add(d1);
                    itemsOrderedS++;
                }
            }
    
            if(missingItems.contains("bird")){
                orderPlaced = true;

                for(int i = 0; i < 3; i++){
                     Bird d1 = (Bird)animFact.createItem("bird", dayNumber);
                     costOfItems += d1.getPurchasePrice();
                     deliveryScheduleS.add(rand.nextInt(3)+1+ dayNumber);
                     deliveredItemsS.add(d1);
                     itemsOrderedS++;
                }
            }
    
            if(missingItems.contains("ferret")){
                orderPlaced = true;

                for(int i = 0; i < 3; i++){
                    Ferret d1 = (Ferret)animFact.createItem("ferret", dayNumber);
                    costOfItems += d1.getPurchasePrice();
                    deliveryScheduleS.add(rand.nextInt(3)+1+ dayNumber);
                    deliveredItemsS.add(d1);
                    itemsOrderedS++;
                }
            }
    
            if(missingItems.contains("snake")){
                orderPlaced = true;

                for(int i = 0; i < 3; i++){
                    Snake d1 = (Snake)animFact.createItem("snake", dayNumber);
                    costOfItems += d1.getPurchasePrice();
                    deliveryScheduleS.add(rand.nextInt(3)+1+ dayNumber);
                    deliveredItemsS.add(d1);
                    itemsOrderedS++;
                }
            }
    
            if(missingItems.contains("leash")){
                orderPlaced = true;

                for(int i = 0; i < 3; i++) {
                     Leash d1 = (Leash) itemFact.createItem("leash", dayNumber);
                     costOfItems += d1.getPurchasePrice();
                     deliveryScheduleS.add(rand.nextInt(3)+1+ dayNumber);
                     deliveredItemsS.add(d1);
                     itemsOrderedS++;
                }
            }
    
            if(missingItems.contains("food")){
                orderPlaced = true;

                for(int i = 0; i < 3; i++) {
                     food d1 = (food) itemFact.createItem("food", dayNumber);
                     costOfItems += d1.getPurchasePrice();
                     deliveryScheduleS.add(rand.nextInt(3)+1+ dayNumber);
                     deliveredItemsS.add(d1);
                     itemsOrderedS++;
                }
            }
    
            if(missingItems.contains("catlitter")){
                orderPlaced = true;

                for(int i = 0; i < 3; i++) {
                     CatLitter d1 = (CatLitter) itemFact.createItem("catLitter", dayNumber);
                     costOfItems += d1.getPurchasePrice();
                     deliveryScheduleS.add(rand.nextInt(3)+1+ dayNumber);
                     deliveredItemsS.add(d1);
                     itemsOrderedS++;
                }
            }
    
            if(missingItems.contains("treats")){
                orderPlaced = true;

                for(int i = 0; i < 3; i++) {
                    Treats d1 = (Treats) itemFact.createItem("treats", dayNumber);
                    costOfItems += d1.getPurchasePrice();
                    deliveryScheduleS.add(rand.nextInt(3)+1+ dayNumber);
                    deliveredItemsS.add(d1);
                    itemsOrderedS++;
                }
            }
    
            if (orderPlaced == true) {
                order_obs.setStore("Southside");
                order_ov.setVal(itemsOrderedS);
                registerS -= costOfItems; //subtract the cost of the order from the register
            }
            
        }
        else {
            System.out.println("ProcessDeliveries() ERROR: char must be N or S.");
        }

 }



    /* ======================================== 8. OpenTheStore() ======================================== */
    // Each item in the inventory array will have a 10% chance of being picked up by a customer
    // Then, there's a 50% the customer buys it for the listed prices
    // If not, the trainer offers a 10% discount, and then there's 75% they buy it with the discount instead
    // When an item is sold it add to a sold items ArrayList, update daySold and salePrice 
    // Money paid goes into the Cash Register. 
    // Assume we will only be selling healthy animals

    // helper function for addOns, used in OpenTheStore
    public double addOnHelper(double basePrice) {
        Random rand = new Random();
        double chips = 0.0;
        double insure = 0.0;
        double vet = 0.0;

        // microchip addOn
        if (rand.nextInt(100) <= 50){
            addOn add1 = new generalAddOn();
            add1 = new Microchip(add1);
            chips += add1.extraCost();
            System.out.println("The customer has added a " + add1.getDescript() + " to the pet for $" + chips);
        }

        // insurance addOn
        if (rand.nextInt(100) <= 50){
            addOn add2 = new generalAddOn();
            add2 = new Insurance(add2);
            insure += add2.extraCost();
            System.out.println("The customer has added " + add2.getDescript() + " to the pet for $" + insure);
        }

        // vet checups addOn
        for (int i = 1; i < 5; i++) {
            if(rand.nextInt(100) <= 25) {
                addOn add3 = new generalAddOn();
                add3 = new vetCheck(add3);
                vet += add3.extraCost();
                System.out.println("The customer has added a " + add3.getDescript() + " for $" + vet);
            }  
        }

        // add add-ons to base price and return
        basePrice = basePrice + chips + insure + vet;
        return basePrice;

    }

    public void OpenTheStore(Employee[] emps, String NS){
        System.out.println("\n ====> The store is now open for customers!");
        Random rand = new Random();

        int numCust = rand.nextInt((10 - 3) + 1) + 3; // there are anywhere between 3 and 10 customers per day
        ArrayList<Pet> healthyPets = new ArrayList<>();
        ArrayList<Item> nonAnimalItems = new ArrayList<>();
        ArrayList<Item> soldItems = new ArrayList<>();
        double register = 0.0;

        if (NS == "N"){
            healthyPets = healthyPetsN;
            nonAnimalItems = nonAnimalItemsN;
            soldItems = soldItemsN;
            customersN += numCust;
            register = registerN;
        }
        else if ( NS == "S") {
            healthyPets = healthyPetsS;
            nonAnimalItems = nonAnimalItemsS;
            soldItems = soldItemsS;
            customersS += numCust;
            register = registerS;
        }
        else {
            System.out.println("OpenTheStore() ERROR: char must be N or S.");
        }

        // Repeat until no more customers are in the store
        for (int i = 0; i < numCust; i++){
            System.out.println("Customer " + (i + 1) + " has just entered the store.");

            // customer goes thorugh every item in healthy animals list
            for (int j = 0; j < healthyPets.size(); j++) {
                double sold = 0.0;
                boolean purchased = false;
                Pet currAnimal = healthyPets.get(j);

                System.out.println("A customer is considering buying a pet.");
                // 50% chance they buy for listed priced
                if (rand.nextInt(100) <= 50) {
                    sold = currAnimal.getListPrice();
                    purchased = true;
                }
                else {
                    System.out.println(emps[1].getName() + " the trainer has offered a 10% discount to the customer for the pet.");

                    // 75% they buy for discounted priced
                    if(rand.nextInt(100) <= 75) {
                        System.out.println("The customer has accepted the discount.");
                        sold = currAnimal.getListPrice() * 0.9;
                        purchased = true;
                    }

                }

                // if a purchase was made, update traits
                if (purchased == true) {
                    double decorate = addOnHelper(sold);
                    sold += decorate;
                    System.out.println("A customer has just purchased a pet for $" + sold);

                    soldItems.add(healthyPets.get(j)); // add to sold items
                    healthyPets.get(j).setSaleInfo(sold, this.day); // update sold price and date
                    register += sold; // increase register
                    healthyPets.remove(j); // remove from inventory
                }
            }

            // customer goes thorugh every item in non-animals list
            for (int j = 0; j < nonAnimalItems.size(); j++) {
                double sold = 0.0;
                boolean purchased = false;

                System.out.println("A customer is considering buying a " + nonAnimalItems.get(j).getName());
                // 50% chance they buy for listed priced
                if (rand.nextInt(100) <= 50) {
                    sold = nonAnimalItems.get(j).getListPrice();
                    purchased = true;
                }
                else {
                    
                    System.out.println(emps[1].getName() + "the trainer has offered a 10% discount to the customer for the item.");

                    // 75% they buy for discounted priced
                    if(rand.nextInt(100) <= 75) {
                        System.out.println("The customer has accepted the discount.");
                        sold = nonAnimalItems.get(j).getListPrice() * 0.9;
                        purchased = true;
                    }

                }
                
                if (purchased == true) {
                    System.out.println("A customer has just purchased a(n) "+ nonAnimalItems.get(j).getName()+ " for $" + sold);

                    soldItems.add(nonAnimalItems.get(j)); // add to sold items
                    nonAnimalItems.get(j).setSaleInfo(sold, this.day); // update sold price and date
                    register += sold; // increase register
                    nonAnimalItems.remove(j); // remove from inventory
                }
            }


            System.out.println("Customer " + (i+1) + " has now left the store.");
        }

        if (NS == "N"){
            healthyPetsN = healthyPets;
            nonAnimalItemsN = nonAnimalItems;
            soldItemsN = soldItems;
            registerN = register;

            visit_obs.setStore("Northside");
            visit_ov.setVisit(customersN);
        }
        else if ( NS == "S") {
            healthyPetsS = healthyPets;
            nonAnimalItemsS = nonAnimalItems;
            soldItemsS = soldItems;
            registerS = register;

            visit_obs.setStore("Southside");
            visit_ov.setVisit(customersS);
        }

    }



    /* ======================================== 9. CleanTheStore() ======================================== */
    //This function has the employees cleaning the store.
    //There is a 5% chance that an animal escapes. When it does, either employee will catch it randomly.

    public void CleanTheStore(Employee[] emps, String NS){
        ArrayList<Pet> healthyPets = new ArrayList<>();
        ArrayList<Pet> sickPets = new ArrayList<>();
        
        if (NS == "N"){
            healthyPets = healthyPetsN;
            sickPets = sickPetsN;
            escape_obs.setStore("Northside");
        }
        else if (NS == "S"){
            healthyPets = healthyPetsS;
            sickPets = sickPetsS;
            escape_obs.setStore("Southside");
        }

        Random rand = new Random();
        System.out.println(emps[1].getName() + " the trainer is cleaning the animal's cages...");
        System.out.println(emps[0].getName() + " the clerk is vacuuming the store...");
        ArrayList<Pet> AllAnimals = new ArrayList(healthyPets);
        AllAnimals.addAll(sickPets);

        for(int i = 0; i < AllAnimals.size(); i++){
            Pet currPet = AllAnimals.get(i);

            if(rand.nextInt(100) <= 5) { //animal escapes while cleaning
                System.out.println("While cleaning, " + currPet.getName() + " the " + currPet.getBreed() + " has escaped!");
                
                if(rand.nextInt(2) % 2 == 0){  //The clerk catches the animal
                    escape_obs.setEmp(emps[0], "clerk");
                    System.out.println("After some time, " + emps[0].getName() + " the clerk has caught the escaped animal!");
                
                }else{ //the trainer catches the animal
                    escape_obs.setEmp(emps[1], "trainer");
                    System.out.println("After some time, " + emps[1].getName() + " the trainer has caught the escaped animal!");
                }

                escape_ov.setEscaped(currPet);
            }
        }
    }

    /* ======================================== 10. LeaveTheStore() ======================================== */
    // This function announces the departure of the two employees and th closing of the store.
    public void LeaveTheStore(Employee[] emps, String NS) {
        String storeLocal = "no location specified";
        if (NS == "N"){
            storeLocal = "Northside";
            leave_obs.setStore(storeLocal);
        }
        else if (NS == "S"){
            storeLocal = "Southside";
            leave_obs.setStore(storeLocal);
        }
        else {
            System.out.println("LeaveTheStore() ERROR: char must be N or S.");
        }

        System.out.println(emps[1].getName() + " the trainer has left for the day.");
        System.out.println(emps[0].getName() + " the clerk has locked up and left for the day.");

        leave_obs.setEmp(emps[0], "clerk");
        leave_ov.setEmp(emps[0]);

        leave_obs.setEmp(emps[1], "trainer");
        leave_ov.setEmp(emps[1]);

        System.out.println("The store is now closed.");
        day++;
    }

    /* ======================================== TrainAnimals() ======================================== */
    public void TrainAnimals(ArrayList<Pet> pets, Employee[] emps){
        System.out.println(emps[1].getName() + " the trainer is going to train the animals!!");
        for(int i = 0; i < pets.size(); i ++){
            emps[1].trainAnimal(pets.get(i));
        }
    }    

    /* ======================================== Command Line interface() ======================================== */
    // asking employee's name
    public void askName(ArrayList<Employee[]> AllEmps, String store) {
        Employee[] emps = {};
        
        if (store == "N") {
            emps = AllEmps.get(0);
        }
        else if (store == "S") {
            emps = AllEmps.get(1);
        }

        System.out.println("\n ==> The current employee assisting you is " + emps[0].getName() + " the clerk");
        // Random rand = new Random();
        // if(rand.nextInt(100) <= 50 ) {
        //     System.out.println("The current employee assisting you is " + emps[0].getName() + " the clerk");
        // }
        // else {
        //     System.out.println("The current employee assisting you is " + emps[1].getName() + " the trainer");
        // }
    }

    // ask for time from employee
    public void askTime(ArrayList<Employee[]> AllEmps, String store) {
        Employee[] emps = {};
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        
        if (store == "N") {
            emps = AllEmps.get(0);

            System.out.println("\n ==> "+ emps[0].getName() + " the clerk has checked the time. It is currently " + sdf.format(cal.getTime()));
        }
        else if (store == "S") {
            emps = AllEmps.get(1);
            System.out.println("\n ==> "+emps[0].getName() + " the clerk has checked the time. It is currently " + sdf.format(cal.getTime()));
        }
    }

    // ask about the inventory
    public void askInventory(ArrayList<Employee[]> AllEmps, String store) {
        Employee[] emps = {};
        Scanner scanner = new Scanner(System.in);
        ArrayList<Item> allItems = new ArrayList<>();
        Item item;
        
        if (store == "N") {
            emps = AllEmps.get(0);
            allItems.addAll(healthyPetsN);
            allItems.addAll(nonAnimalItemsN);
            System.out.println("\n ==> "+emps[1].getName() + " the trainer is happy to give you the inventory.");
        }
        else if (store == "S") {
            emps = AllEmps.get(1);
            allItems.addAll(healthyPetsS);
            allItems.addAll(nonAnimalItemsS);
            System.out.println("\n ==> "+emps[1].getName() + " the trainer is happy to give you the inventory.");
        }

        printRemains(store);

    }

    // ask about the inventory
    public void askDetails(String store) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Item> allItems = new ArrayList<>();
        Item item = null;
        
        if (store == "N") {
            allItems.addAll(healthyPetsN);
            allItems.addAll(nonAnimalItemsN);
        }
        else if (store == "S") {
            allItems.addAll(healthyPetsS);
            allItems.addAll(nonAnimalItemsS);
        }

        System.out.println("\n ==> Let's first take a looka at the inventory");
        printRemains(store);

        if (allItems.size() <= 0) {
            System.out.println("Sorry, there are no more items left in stock.");
            return;
        }

        while (true) {
            System.out.println("\n ==> Which item would you like to know details about? Enter a number corresponding to an item: ");
            int iNum = scanner.nextInt();

            if ((iNum >=0) && (iNum < allItems.size())) {
                item = allItems.get(iNum);
                System.out.println(item.getName() + " costs " + item.getListPrice());
                break;
            }
            else {
                System.out.println("This item is not in the store's category. Try again...");
            }
        }
    }
    
    // buy an item
    public void askToBuy(ArrayList<Employee[]> AllEmps, String store) {
        Employee[] emps = {};
        Scanner scanner = new Scanner(System.in);
        ArrayList<Item> allItems = new ArrayList<>();
        Item item;

        if (store == "N") {
            emps = AllEmps.get(0);
            allItems.addAll(healthyPetsN);
            allItems.addAll(nonAnimalItemsN);
        }
        else if (store == "S") {
            emps = AllEmps.get(1);
            allItems.addAll(healthyPetsS);
            allItems.addAll(nonAnimalItemsS);
        }

        System.out.println("\n ==>Let's first take a looka at the inventory");
        printRemains(store);

        if (allItems.size() <= 0) {
            System.out.println("Sorry there are no more items left in stock to buy... ");
            return;
        }

        while (true) {
            System.out.println("Enter a number corresponding to an item: ");
            int iNum = scanner.nextInt();
            Random rand = new Random();

            if ((iNum >=0) && (iNum < allItems.size())) {
                item = allItems.get(iNum);
                System.out.println("You're considering buying a " + item.getName() + ", which costs " + item.getListPrice());

                double sold = 0.0;
                boolean purchased = false;

                // 50% chance they buy for listed priced
                if (rand.nextInt(100) <= 50) {
                    sold = item.getListPrice();
                    purchased = true;
                }
                else {
                    System.out.println(emps[1].getName() + " the trainer has offered you a 10% discount for the item.");

                    // 75% they buy for discounted priced
                    if(rand.nextInt(100) <= 75) {
                        System.out.println("You've has accepted the discount.");
                        sold = item.getListPrice() * 0.9;
                        purchased = true;
                    }
                    else {
                        System.out.println("You've refused the discount.");
                    }
                }

                // if a purchase was made, update traits
                if (purchased == true) {
                    System.out.println("You just purchased a(n) " + item.getName() + " for $" + sold);

                    if (store == "N") {
                        soldItemsN.add(item);
                        registerN += sold;

                        if(healthyPetsN.contains(item)) {
                            int idx = healthyPetsN.indexOf(item);
                            item.setSaleInfo(sold, this.day);
                            healthyPetsN.remove(idx);
                        }
                        else if(nonAnimalItemsN.contains(item)) {
                            int idx = nonAnimalItemsN.indexOf(item);
                            item.setSaleInfo(sold, this.day);
                            nonAnimalItemsN.remove(idx);
                        }
                    }
                    if (store == "S") {
                        soldItemsS.add(item);
                        registerS += sold;

                        if(healthyPetsS.contains(item)) {
                            int idx = healthyPetsS.indexOf(item);
                            item.setSaleInfo(sold, this.day);
                            healthyPetsS.remove(idx);
                        }
                        else if(nonAnimalItemsS.contains(item)) {
                            int idx = nonAnimalItemsS.indexOf(item);
                            item.setSaleInfo(sold, this.day);
                            nonAnimalItemsS.remove(idx);
                        }
                    }
                }
                else {
                    System.out.println("You've decided not to purchase " + item.getName());
                }
                break;
            }
            else {
                System.out.println("This item is not in the store's category. Try again...");
            }
        }
    }


    // selcting a store location
    public String selectStore() {
        Scanner scanner = new Scanner(System.in);
        String store;

        while (true) {
            System.out.println("Which store would you like to visit today?");
            System.out.println("(1) Northside Location");
            System.out.println("(2) Southside Location");
            int storeOption = scanner.nextInt();
            System.out.println(storeOption);
            if (storeOption == 1){
                store = "N";
                System.out.println("Welcome to the Northside Location!");
                break;
            }
            else if (storeOption == 2){
                store = "S";
                System.out.println("Welcome to the Southside Location!");
                break;
            }
            else {
                System.out.println("ERROR: Please type 1 or 2");
            }
        }

        return store;
    }

    /* ======================================== Simulate() ======================================== */


    public void simulateNDays(int n){
        // initialize inventory of 3 per subclass (Dog, Toy, etc)
        ConcrNAnimFact itemFact = new ConcrNAnimFact();
        ConcrAnimFact animFact = new ConcrAnimFact();
        for(int i = 0; i < 3; i++){
            // Northside stock
            healthyPetsN.add((Pet)animFact.createItem("dog", 0));
            healthyPetsN.add((Pet)animFact.createItem("cat", 0));
            healthyPetsN.add((Pet)animFact.createItem("bird", 0));
            healthyPetsN.add((Pet)animFact.createItem("snake", 0));
            healthyPetsN.add((Pet)animFact.createItem("ferret", 0));
            nonAnimalItemsN.add(itemFact.createItem("catLitter", 0));
            nonAnimalItemsN.add(itemFact.createItem("treats", 0));
            nonAnimalItemsN.add(itemFact.createItem("food", 0));
            nonAnimalItemsN.add(itemFact.createItem("toy", 0));
            nonAnimalItemsN.add(itemFact.createItem("leash", 0));

            // Southside stock
            healthyPetsS.add((Pet)animFact.createItem("dog", 0));
            healthyPetsS.add((Pet)animFact.createItem("cat", 0));
            healthyPetsS.add((Pet)animFact.createItem("bird", 0));
            healthyPetsS.add((Pet)animFact.createItem("snake", 0));
            healthyPetsS.add((Pet)animFact.createItem("ferret", 0));
            nonAnimalItemsS.add(itemFact.createItem("catLitter", 0));
            nonAnimalItemsS.add(itemFact.createItem("treats", 0));
            nonAnimalItemsS.add(itemFact.createItem("food", 0));
            nonAnimalItemsS.add(itemFact.createItem("toy", 0));
            nonAnimalItemsS.add(itemFact.createItem("leash", 0));

        }

        //System.out.println("Through item creation");


        Logger dayLog = new Logger(); // Logger: eager instantiation

        // register all observers
        arrive_ov.register(arrive_obs);
        invCount_ov.register(invCount_obs);
        regOv.register(regObs); 
        bank_ov.register(bank_obs); 
        in_Val.register(in_val_obs);
        items_ov.register(items_obs);
        order_ov.register(order_obs);
        visit_ov.register(visit_obs);
        escape_ov.register(escape_obs);
        leave_ov.register(leave_obs);

        // Simulate for n amount of days, performing each method
        for(int i = 0; i < n; i++){
            // creating file for this day
            String logName = "Logger-" + (i+1) + ".txt";
            dayLog.setName(logName);

            // pass Logger to all observers
            arrive_obs.setFile(dayLog);
            bank_obs.setFile(dayLog);
            escape_obs.setFile(dayLog);
            invCount_obs.setFile(dayLog);
            in_val_obs.setFile(dayLog);
            items_obs.setFile(dayLog);
            regObs.setFile(dayLog);
            order_obs.setFile(dayLog);
            visit_obs.setFile(dayLog);
            leave_obs.setFile(dayLog);
            

            System.out.println("\n=========== Starting Day " + i + "! ===========");
            ArrayList<Employee[]> AllEmps = ArriveAtStore();
            Employee [] NorthEmps = AllEmps.get(0);
            Employee [] SouthEmps = AllEmps.get(1);

            /* === NORTHSIDE STORE */
            dayLog.writeLog("\n\n** NORTHSIDE LOG **\n"); 
            // pass employees to necessary observers 
            in_val_obs.setEmp(NorthEmps[0]); // this takes a clerk
            regObs.setEmp(NorthEmps[0]); // takes clerk
            bank_obs.setEmp(NorthEmps[0]); // takes clerk
            items_obs.setEmp(NorthEmps[0]); // takes clerk
            order_obs.setEmp(NorthEmps[0]); //takes clerk

            ProcessDeliveries("N");
            FeedAnimals("N");
            CheckRegister(NorthEmps, "N");
            ArrayList<String> missingItems = DoInventory("N");
            PlaceAnOrder(missingItems, i, "N");
            ArrayList<Pet> allAnimalsN = healthyPetsN;
            allAnimalsN.addAll(sickPetsN);
            TrainAnimals(allAnimalsN, NorthEmps);
            OpenTheStore(NorthEmps, "N");
            CleanTheStore(NorthEmps, "N");
            LeaveTheStore(NorthEmps, "N");

            /* === SOUTHSIDE STORE */
            dayLog.writeLog("\n\n** SOUTHSIDE LOG **\n");
            // pass employees to necessary observers 
            in_val_obs.setEmp(SouthEmps[0]); // this takes a clerk
            regObs.setEmp(SouthEmps[0]); // takes clerk
            bank_obs.setEmp(SouthEmps[0]); // takes clerk
            items_obs.setEmp(SouthEmps[0]); // takes clerk
            order_obs.setEmp(SouthEmps[0]); //takes clerk

            ProcessDeliveries("S");
            FeedAnimals("S");
            CheckRegister(NorthEmps, "S");
            ArrayList<String> missingItemsS = DoInventory("S");
            PlaceAnOrder(missingItemsS, i, "S");
            ArrayList<Pet> allAnimalsS = healthyPetsS;
            allAnimalsS.addAll(sickPetsS);
            TrainAnimals(allAnimalsS, SouthEmps);
            OpenTheStore(SouthEmps, "S");
            CleanTheStore(SouthEmps, "S");
            LeaveTheStore(SouthEmps, "S");

        }

        /* ==== Command Line Interface ==== */
        String logName = "Logger-" + (n+1) + ".txt";
        dayLog.setName(logName);
        dayLog.writeLog("\nThis is the Logger for the user interaction\n");

        // pass Logger to all observers
        arrive_obs.setFile(dayLog);
        bank_obs.setFile(dayLog);
        escape_obs.setFile(dayLog);
        invCount_obs.setFile(dayLog);
        in_val_obs.setFile(dayLog);
        items_obs.setFile(dayLog);
        regObs.setFile(dayLog);
        order_obs.setFile(dayLog);
        visit_obs.setFile(dayLog);
        leave_obs.setFile(dayLog);
        

        System.out.println("\n=========== Starting User Interaction on Day " + n + "! ===========");
        ArrayList<Employee[]> AllEmps = ArriveAtStore();
        Employee [] NorthEmps = AllEmps.get(0);
        Employee [] SouthEmps = AllEmps.get(1);

        // PREPRARING NORTHSIDE
        in_val_obs.setEmp(NorthEmps[0]); // this takes a clerk
        regObs.setEmp(NorthEmps[0]); // takes clerk
        bank_obs.setEmp(NorthEmps[0]); // takes clerk
        items_obs.setEmp(NorthEmps[0]); // takes clerk
        order_obs.setEmp(NorthEmps[0]); //takes clerk

        ProcessDeliveries("N");
        FeedAnimals("N");
        CheckRegister(NorthEmps, "N");
        ArrayList<String> missingItems = DoInventory("N");
        PlaceAnOrder(missingItems, n, "N");
        ArrayList<Pet> allAnimalsN = healthyPetsN;
        allAnimalsN.addAll(sickPetsN);
        TrainAnimals(allAnimalsN, NorthEmps);

        // PREPARING SOUTHSIDE
        in_val_obs.setEmp(SouthEmps[0]); // this takes a clerk
        regObs.setEmp(SouthEmps[0]); // takes clerk
        bank_obs.setEmp(SouthEmps[0]); // takes clerk
        items_obs.setEmp(SouthEmps[0]); // takes clerk
        order_obs.setEmp(SouthEmps[0]); //takes clerk

        ProcessDeliveries("S");
        FeedAnimals("S");
        CheckRegister(NorthEmps, "S");
        ArrayList<String> missingItemsS = DoInventory("S");
        PlaceAnOrder(missingItemsS, n, "S");
        ArrayList<Pet> allAnimalsS = healthyPetsS;
        allAnimalsS.addAll(sickPetsS);
        TrainAnimals(allAnimalsS, SouthEmps);
        // OpenTheStore(SouthEmps, "S");
        // CleanTheStore(SouthEmps, "S");
        // LeaveTheStore(SouthEmps, "S");

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n Welcome customer!\n");
        String store = selectStore();
        
        int option;

        while (true) {
            if (store == "N") {
                System.out.println("\n(Northside location)");
            }
            else if (store == "S") {
                System.out.println("\n(Southside location)");
            }
            System.out.println("Please select an action by typing the corresponding character:");
            System.out.println("(1) Ask the assisting employee their name");
            System.out.println("(2) Ask for the time of day");
            System.out.println("(3) Request the current store inventory");
            System.out.println("(4) Ask detail about a certain item");
            System.out.println("(5) Buy an item");
            System.out.println("(6) Select a different store location");
            System.out.println("(0) Exit store (this will end user interactions)");

            option = scanner.nextInt();

            if (option == 1){
                askName(AllEmps, store);
            }
            else if (option == 2){
                askTime(AllEmps, store);
            }
            else if (option == 3){
                askInventory(AllEmps, store);
            }
            else if (option == 4){
                askDetails(store);
            }
            else if (option == 5){
                askToBuy(AllEmps, store);
            }
            else if (option == 6){
                store = selectStore();
            }
            else if (option == 0) {
                System.out.println("You have finished shopping. Have a good day!");
                break;
            }
            else {
                System.out.println("This is not a valid input. Try again...");
            }
        }

        CleanTheStore(NorthEmps, "N");
        LeaveTheStore(NorthEmps, "N");

        CleanTheStore(SouthEmps, "S");
        LeaveTheStore(SouthEmps, "S");


        // Final summary after 
        System.out.println("\n=========== Final Summary ===========");

        /* NORTHSIDE SUMMARY */
        double totN = getInventoryTotal("N");
        double salesN = getSaleTotal("N");
        System.out.println("\n( Northside Branch )");
        System.out.println("\nThe total inventory value is: $" + totN);
        printRemains("N");
        System.out.println("\nThe total sales value is: $" + salesN);
        printSales("N");
        printSick("N");
        System.out.println("\nThe final count of the register is: $" + registerN);
        System.out.println("\nThe total money added to the register from GoToBank(): $" + bankDebtN);

        /* SOUTHSIDE SUMMARY */
        double totS = getInventoryTotal("S");
        double salesS = getSaleTotal("S");
        System.out.println("\n( Southside Branch )");
        System.out.println("\nThe total inventory value is: $" + totS);
        printRemains("S");
        System.out.println("\nThe total sales value is: $" + salesS);
        printSales("S");
        printSick("S");
        System.out.println("\nThe final count of the register is: $" + registerS);
        System.out.println("\nThe total money added to the register from GoToBank(): $" + bankDebtS);
        
    }
}