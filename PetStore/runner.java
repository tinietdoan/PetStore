import itemPackage.*;
import employeePackage.*;
import simulationPackage.*;
import observerPackage.*;
import java.io.*;
import java.util.*;

public class runner{


    public static void main(String args[]){
//        /* === Item Package basic tests === */
//        Item testI = new Item("box", 1.0,1.0,0);
//        Pet testP = new Pet("Pitbull", 1, true);
//        Dog testD = new Dog("heafty chonker", "brindle", true, false);
//        Cat testC = new Cat("tabby", false, true);
//        Bird testB = new Bird("small-ish", false, false, true);
//        food testF = new food("large", "Dog", "Tasty Fud");
//        Leash testL = new Leash("Dog");
//        Toy testT = new Toy("Dog");
//        CatLitter testCL = new CatLitter("Medium");
//        testD.setPetInfo("Pitbull", 2, true);
//        System.out.println(testD.getBreed());


//        /* === Employee Package basic tests === */
//        Employee testEm = new Employee("Randal");
//        System.out.println(testEm.getName());
//        Clerk testCl = new Clerk("Sarah");
//        System.out.println(testCl.getDaysWorked());
//        testCl.incrDaysWorked();
//        System.out.println(testCl.getDaysWorked());
//        System.out.println(testCl.getName());
//        Trainer testTr = new Trainer("Gilbert");
//        System.out.println(testTr.getName());
//

          /* === Simulation basic tests === */

//        //Create employees
//        Clerk[] clerkList = new Clerk[2];
//        Trainer[] trainerList = new Trainer[2];
//        clerkList[0] = new Clerk("Max");
//        clerkList[1] = new Clerk("Anya");
//        trainerList[0] = new Trainer("Zander");
//        trainerList[1] = new Trainer("Kendall");
//        ArrayList<Pet> hPets = new ArrayList<Pet>();
//
//        Simulation sim = new Simulation(clerkList, trainerList);
//        System.out.println("Employees arive: ");
//        //test Arrive at store
//
//        for(int i = 0; i < 30; i++){
//            System.out.println("Starting day " + i "!");
//            Employee [] todaysEmps = sim.ArriveAtStore();
//            sim.processDeliveries();
//            sim.FeedAnimals();
//            sim.CheckRegister(todaysEmps);
//            ArrayList<String> missingItems = sim.DoInventory();
//            sim.PlaceAnOrder(missingItems, i);
//            sim.OpenTheStore(todaysEmps);
//            sim.CleanTheStore(todaysEmps);
//            sim.LeaveTheStore(todaysEmps);
//
//        }


//        //create sim Object
//        Simulation sim = new Simulation(clerkList, trainerList);
//        System.out.println("Employees arive: ");
//        //test Arrive at store
//        Employee [] todaysEmps = sim.ArriveAtStore();
////        Employee [] todaysEmps2 = sim.ArriveAtStore();
////        Employee [] todaysEmps3 = sim.ArriveAtStore();
////        Employee [] todaysEmps4 = sim.ArriveAtStore();
//
//
//        //Test animal generators
//        ArrayList<Pet> hPets = new ArrayList<Pet>();
//        hPets.add(sim.genDog(1));
//        hPets.add(sim.genDog(1));
//        hPets.add(sim.genCat(1));
//        hPets.add(sim.genBird(1));
//
//        ArrayList<Pet> sPets = new ArrayList<Pet>();
//        sPets.add(sim.genDog(1));
//        sPets.add(sim.genCat(1));
//
//        //load animals into sim
//        sim.addPets(hPets, sPets);
//
//
//        //test feed animals
//        System.out.println("Feeding Pets: ");
//        sim.FeedAnimals();
//        //test clean the store
//        System.out.println("Cleaning the store:  ");
//        sim.CleanTheStore(todaysEmps);
//
//        //Test Place an order
//        ArrayList<String> missingItems = new ArrayList<String>(Arrays.asList("dog","cat","bird","leash","toy","food","catlitter"));
//        System.out.println(sim.getDeliveredItems());
//        sim.PlaceAnOrder(missingItems, 0);
//        System.out.println(sim.getDeliveredItems().size());
//        System.out.println(sim.getDeliverySchedule().size());

        // test ProcessDeliveries
//        System.out.println("Processing Deliveries... " );
//        System.out.println(sim.getDeliveredItems());
//        sim.ProcessDeliveries();
        //Create employees
//        Clerk[] clerkList = new Clerk[2];
//        Trainer[] trainerList = new Trainer[2];
//        clerkList[0] = new Clerk("Max");
//        clerkList[1] = new Clerk("Anya");
//        trainerList[0] = new Trainer("Zander");
//        trainerList[1] = new Trainer("Kendall");
//        Simulation sim = new Simulation(clerkList, trainerList);
//        sim.simulateNDays(30);
//        System.out.println("end");

//<=============================Simulation Runner==================================>//
        //Executable for the Sumilation:

        //Answer for P4 Part 2, Question 2: How many employees do we need?
            //Answer: 4 of each type.
            //Algorithmically, if the sick mechanism wasn't there, you would only need 3 clerks, and 3 trainers. However,
            // due to the randomized nature of the sickness, you can't use the algorithmic solution. Because there is a chance that all of the
            // employees could be sick on a single day, there is no amount of employees that could ensure that there are an availble 4.
            //That being said, the more employees you have, the less likely that there will be no avaible employees.
            //The most likely solution is to have 4 employees of each type (clerk and trainer). 3 ensure that there won't be an employee working
            //more than three days in a row, and one more of each category to cover the sickness mechanic.
            //However, to further lower the chance of all employees being unavaible, 5 employees could also be hired.

        Employee[] clerkList = new Clerk[4];
        Employee[] trainerList = new Trainer[4];
        ConcrEmpFact empFact = new ConcrEmpFact();
        clerkList[0] = empFact.makeEmp(new String[]{"clerk", "Max"});
        clerkList[1] = empFact.makeEmp(new String[]{"clerk", "Anya"});
        clerkList[2] = empFact.makeEmp(new String[]{"clerk", "Corben"});
        clerkList[3] = empFact.makeEmp(new String[]{"clerk", "Trent"});
        trainerList[0] = empFact.makeEmp(new String[]{"trainer", "Zander", "haphazard"});
        trainerList[1] = empFact.makeEmp(new String[]{"trainer", "Kendall","negitiveRenfT"});
        trainerList[2] = empFact.makeEmp(new String[]{"trainer", "Evan", "positiveRenfT"});
        trainerList[3] = empFact.makeEmp(new String[]{"trainer", "Quinlan", "positiveRenfT"});
        Simulation sim = new Simulation(clerkList, trainerList);
        //Employee[] emps = new Employee[]{clerkList[0], trainerList[1]};
        //System.out.println(sim.getEligibleEmp(clerkList).getName());
//        System.out.println("d1");
//        sim.ArriveAtStore();
//        System.out.println("d2");
//        sim.ArriveAtStore();
//        System.out.println("d3");
//        sim.ArriveAtStore();
//        System.out.println("d4");
//        sim.ArriveAtStore();
//        System.out.println("d5");
//        sim.ArriveAtStore();

//        ArrayList<Pet> hPets = new ArrayList<Pet>();
//        ConcrAnimFact animFact = new ConcrAnimFact();
//        for(int i = 0; i < 3; i ++){
//            hPets.add((Pet)animFact.createItem("dog", 0));
//        }
        //sim.TrainAnimals(hPets, emps);
//
//        ArrayList<Item> nonAnimalItems = new ArrayList<Item>();
//
//        System.out.println(nonAnimalItems);
//


        sim.simulateNDays(16);

    }
}