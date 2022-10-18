package itemPackage;
import java.util.*;
public class ConcrNAnimFact extends abstItemFact {

    ArrayList<String> sizes = new ArrayList<String>(Arrays.asList("small", "medium", "large", "very large"));
    ArrayList<String> animals = new ArrayList<String>(Arrays.asList("Bird", "Dog", "Cat"));
    ArrayList<String> foodType = new ArrayList<String>(Arrays.asList("Dry Food", "Wet Food", "Treats"));

    public int pickRandFromList(int size){
        Random rand = new Random();
        return rand.nextInt(size);
    }

    //Argumnets List:
    //args is the type of non animal item
    //catLitter, leash, toy, food, treats
    public Item makeItem(String args){
        if(args.equals("catLitter")){
            String size = sizes.get(pickRandFromList(sizes.size()));
            return new CatLitter(size);
        }else if(args.equals("leash")){
            String animal = animals.get(pickRandFromList(animals.size()));
            return new Leash(animal);
        }else if(args.equals("toy")){
            String animal = animals.get(pickRandFromList(animals.size()));
            return new Toy(animal);
        }else if(args.equals("food")){
            String animal = animals.get(pickRandFromList(animals.size()));
            String foodtype = foodType.get(pickRandFromList(foodType.size()));
            return new food(animal + " " + foodtype + " food",animal, foodtype );
        }else if(args.equals("treats")) {
            String animal = animals.get(pickRandFromList(animals.size()));
            return new Treats(animal);
        }else{
            return null;
        }
    }
}