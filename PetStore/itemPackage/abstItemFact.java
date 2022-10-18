package itemPackage;

public abstract class abstItemFact{

    public Item createItem(String args, int day){
        Item tempItem = makeItem(args);
        if(tempItem instanceof PetInterface){
            ((PetInterface)tempItem).setPetInfo();
        }
        tempItem.setItemInfo(day);
        return tempItem;
    }

    public abstract Item makeItem(String args);
}