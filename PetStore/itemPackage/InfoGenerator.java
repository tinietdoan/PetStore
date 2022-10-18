package itemPackage;
import java.util.*;

public class InfoGenerator {
    ArrayList<String> names20 = new ArrayList<String>(Arrays.asList("Albus", "Stan", "Prince Schnookums", "Freddie", "Sam", "Bailey", "Sooty", "Whiskey", "Ted", "Pheonix",
            "Misty", "Bella", "Crumble", "Olive", "Cleo", "Coco", "Honey", "Pickle", "Rosie", "Nala"));
    ArrayList<String> sizes = new ArrayList<String>(Arrays.asList("small", "medium", "large", "very large"));
    ArrayList<String> dogBreed = new ArrayList<String>(Arrays.asList("Pitbull", "Lab", "Poodle"));
    ArrayList<String> dogColor = new ArrayList<String>(Arrays.asList("Brown", "Tan", "Spotted"));
    ArrayList<String> catBreed = new ArrayList<String>(Arrays.asList("Tabby", "Russian Blue", "Orange"));
    ArrayList<String> ferretBreed = new ArrayList<String>(Arrays.asList("Albino Ferret", "Black Mitt Ferret", "Cinamon Mitt Ferret"));
    ArrayList<String> snakeBreed = new ArrayList<String>(Arrays.asList("CornSnake", "Python", "GarterSnake"));
    ArrayList<String> birdBreed = new ArrayList<String>(Arrays.asList("Parrot", "Pigeon", "Cocatrice"));
    ArrayList<Boolean> boolist = new ArrayList<>(Arrays.asList(true, false));

    public int pickRandFromList(int size) {
        Random rand = new Random();
        return rand.nextInt(size);
    }

    public String getInfo(String args) {
        if (args.equals("name")) {
            return names20.get(pickRandFromList(names20.size()));
        } else if (args.equals("dogBreed")) {
            return dogBreed.get(pickRandFromList(dogBreed.size()));
        } else if (args.equals("catBreed")) {
            return catBreed.get(pickRandFromList(catBreed.size()));
        } else if (args.equals("birdBreed")) {
            return birdBreed.get(pickRandFromList(birdBreed.size()));
        } else if (args.equals("snakeBreed")) {
            return snakeBreed.get(pickRandFromList(snakeBreed.size()));
        } else if (args.equals("ferretBreed")) {
            return ferretBreed.get(pickRandFromList(ferretBreed.size()));
        } else if (args.equals("size")) {
            return sizes.get(pickRandFromList(sizes.size()));
        } else if (args.equals("color")) {
            return dogColor.get(pickRandFromList(dogColor.size()));
        } else {
            return null;
        }
    }

    public Boolean getBool(){
        return boolist.get(pickRandFromList(boolist.size()));
    }

    public int getNum(int min, int max){
        Random rand = new Random();
        return rand.nextInt(max-min) + min;
    }

}