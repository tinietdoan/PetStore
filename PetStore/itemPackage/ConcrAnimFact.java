package itemPackage;
import java.util.*;
public class ConcrAnimFact extends abstItemFact {

    ArrayList<String> sizes = new ArrayList<String>(Arrays.asList("small", "medium", "large", "very large"));
    ArrayList<String> dogColor = new ArrayList<String>(Arrays.asList("Brown", "Tan", "Spotted"));
    ArrayList<Boolean> boolist = new ArrayList<>(Arrays.asList(true,false));

    public int pickRandFromList(int size) {
        Random rand = new Random();
        return rand.nextInt(size);
    }

    public Item makeItem(String args){
        if(args.equals("dog")){
            String size = sizes.get(pickRandFromList(sizes.size()));
            String color = dogColor.get(pickRandFromList(dogColor.size()));
            boolean bool1 = boolist.get(pickRandFromList(boolist.size()));
            boolean bool2 = boolist.get(pickRandFromList(boolist.size()));
            return new Dog(size, color, bool1, bool2);
        }else if(args.equals("cat")){
            String size = sizes.get(pickRandFromList(sizes.size()));
            boolean bool1 = boolist.get(pickRandFromList(boolist.size()));
            boolean bool2 = boolist.get(pickRandFromList(boolist.size()));
            return new Cat(size, bool1, bool2);
        }else if(args.equals("bird")){
            String size = sizes.get(pickRandFromList(sizes.size()));
            boolean bool1 = boolist.get(pickRandFromList(boolist.size()));
            boolean bool2 = boolist.get(pickRandFromList(boolist.size()));
            boolean bool3 = boolist.get(pickRandFromList(boolist.size()));
            return new Bird(size, bool1, bool2, bool3);
        }else if(args.equals("snake")){
            String size = sizes.get(pickRandFromList(sizes.size()));
            return new Snake(size);
        }else if(args.equals("ferret")){
            String color = dogColor.get(pickRandFromList(dogColor.size()));
            boolean bool1 = boolist.get(pickRandFromList(boolist.size()));
            return new Ferret(color, bool1);
        }else{
            return null;
        }
    }



}