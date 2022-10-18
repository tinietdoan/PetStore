package Items;
import Actions.*;

//Tools can increase the efficiency of the player's actions, making them require less energy points
//They hold actionType variables, which store what type of action they enhance.
public class Tool extends Item{

    actionType proficiency;

    public Tool(){
        super.price = 9.0;
    }


    //This function sets the tool's action proficiency.
    public void setProf(){
        itemDataHolder idh = new itemDataHolder();
        this.proficiency = idh.getActionType(this.getName());
    }
   // public Tool(){System.out.println("Need to implement correct Tool factory.");}

    public String getActionStr(){
        return proficiency.toString();
    }
}

