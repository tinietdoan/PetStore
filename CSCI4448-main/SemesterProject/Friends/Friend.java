package Friends;

import java.io.*;
import java.util.*;


import Items.*;
import Actions.*;

public class Friend {
    String name;
    int mood;
    actionType action; 
    actionType proficient;
    ArrayList<Item> inventory;
    ArrayList<String> regDialogue;
    ArrayList<String> moodUpDialogue;

    public Friend() {
        this.name = "";
        this.mood = 0;
        this.inventory = new ArrayList<Item>();
        this.regDialogue = new ArrayList<String>();
        this.moodUpDialogue = new ArrayList<String>();
        this.proficient = null;
    }

    //getters
    public String getName(){
        return this.name;
    }
    public int getMood(){
        return this.mood;
    }

    public actionType getAction(){
        return this.action;
    }

    public ArrayList<Item> getInventory() {
        return this.inventory;
    }

    public actionType getProficient() {
        return this.proficient;
    }

    public String getOneRegString() {
        Random rand = new Random();

        int i = rand.nextInt(regDialogue.size());
        return regDialogue.get(i);
    }

    public String getOneMoodString() {
        Random rand = new Random();

        int i = rand.nextInt(moodUpDialogue.size());
        return moodUpDialogue.get(i);
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }
    public void setMood(int mood) {
        this.mood = mood;
    }
    public void setAction(actionType action){
        this.action = action;
    }
    public void setProficient(actionType prof){
        this.proficient = prof;
    }


    // other methods
    public void addMoodString(String happy) {
        moodUpDialogue.add(happy);
    }
    public void addRegString(String talk) {
        regDialogue.add(talk);
    }


    public void moodUp(){
        this.mood++;
        System.out.println(name +"\'s mood has improved!\n");
    }

    public void DoAction(){
        this.action.doAction(this);

        if (this.action == this.proficient) {
            String happy = getOneMoodString();
            System.out.println(happy);
            moodUp();
        }

        return;
    }

    public void interact(){
        String talk = getOneRegString();
        System.out.println(talk);
        moodUp();
    }
}
