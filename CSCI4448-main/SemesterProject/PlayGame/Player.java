package PlayGame;

import Items.*;
import Friends.*;
import Events.*;
import java.util.*;

//The player class hold all of the player specific statistics.
//Notable, it hold the name, the amount of money, and the energy points for the current day.
// Singleton Pattern
public class Player {
    String name;
    Double money;
    int energyPoints;


    public Player() {
        name = "";
        money = 50.0;
        energyPoints = 10;
    }

    // =========== Getters ===========
    public String getName() {
        return this.name;
    }
    public Double getMoney(){
        return this.money;
    }
    public int getEnergyPoints(){
        return this.energyPoints;
    }


    // =========== Setters ===========
    public void setName(String n) {
        this.name = n;
        //return;
    }
    public void setEnergy(int num) {
        this.energyPoints = num;
        return;
    }

    // =========== Update methods ===========
    // add money 
    public void addMoney(Double m) {
        money += m;
        return;
    }
    // decrease energy points
    public void decre_Energy(int decr) {
        energyPoints -= decr;
    }

    // check if the player still has energy
    public boolean tired() {
        if (energyPoints <= 0) {
            return true;
        }
        return false;
    }


}
