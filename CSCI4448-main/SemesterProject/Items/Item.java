package Items;

//Highest level of the item hierarchy
//all items extend this class

public abstract class Item{
    String name;
    Double price;

    public String getName(){
        return name;
    }

    public Double getPrice(){
        return price;
    }

    void setItemInfo(String Name, Double Price){
        name = Name;
        price = Price;
    }
}