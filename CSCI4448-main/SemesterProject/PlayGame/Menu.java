package PlayGame;

import Friends.Friend;
import java.util.*;
import Items.GardenPlot;
import java.util.Scanner;

//This class provided the UI to the player
//It is broken into 8 different 'screens'
//The menus provide an interface for the user to select actions, and it provides displays of information



public class Menu {
    public String getName(Game game){
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String navInd;
        System.out.println("\n///////////////// HAPPY GO FARMING /////////////////\n" +
                "Welcome to Happy Go Farming! ................\n" +
                "Please enter your name:......................\n"
        );
        // game.player.setName(myObj.nextLine());
        return myObj.nextLine();  // Read user input

    }


    public String menu1(){
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String navInd;
        while(true) {
            System.out.println("\n///////////////// HAPPY GO FARMING /////////////////\n" +
                    "........Main Menu............Enter Nav Input!\n" +
                    ".1: Manage Garden............................\n" +
                    ".2: Interact With Friends....................\n" +
                    ".3: Visit Marketplace........................\n" +
                    ".4: Check Inventory..........................\n" +
                    ".5: Display Progress and Stats...............\n" +
                    ".6: End Day..................................\n");

            navInd = myObj.nextLine();  // Read user input
            if(!(navInd.equals("1") ||navInd.equals("2")  ||navInd.equals("3")  ||navInd.equals("4") ||navInd.equals("5") || navInd.equals("6") )){
                System.out.println("Enter Valid Option (ex. 1, 2, 3, ...)");
            }else{
                break;
            }
        }
        return navInd;
    }

    public String selectGPlotMenu(ArrayList<GardenPlot> gplots){
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String navInd;
        String[] menuGPlots = new String[]{"<Unowned Plot>","<Unowned Plot>","<Unowned Plot>","<Unowned Plot>" };
        for(int i = 1; i < gplots.size(); i ++){
            menuGPlots[i-1] = "Garden Plot " + (i+1);

        }
        while(true) {
            System.out.println("\n///////////////// HAPPY GO FARMING /////////////////\n" +
                    "........Select Garden Plot.......0: Prev. Menu\n" +
                    ".1: Garden Plot 1............................\n" +
                    ".2: "+ menuGPlots[0] +"...........................\n" +
                    ".3: "+ menuGPlots[1] +"...........................\n" +
                    ".4: "+ menuGPlots[2] +"...........................\n" +
                    ".5: "+ menuGPlots[3] +"...........................\n");

            navInd = myObj.nextLine();  // Read user input
            if(!(navInd.equals("1") ||navInd.equals("2")  ||navInd.equals("3")  ||navInd.equals("4") ||navInd.equals("5")||navInd.equals("0"))){
                System.out.println("Enter Valid Option (ex. 1, 2, 3, ...)");
            }else if(!(navInd.equals("0") || navInd.equals("1") ) && menuGPlots[Integer.parseInt(navInd)-2].equals("<Unowned Plot>")){
                System.out.println("Select Garden Plot You Own");
            }else{
                break;
            }
        }
        return navInd;
    }

    public String ManageGardenMenu(){
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String navInd;
        while(true) {
            System.out.println("\n///////////////// HAPPY GO FARMING /////////////////\n" +
                    "........Manage Garden...........0: Prev. Menu\n" +
                    "......(each action costs 2 energy points).....\n" +
                    ".1: Water Plants.............................\n" +
                    ".2: Plant Seeds..............................\n" +
                    ".3: Harvest Plants...........................\n" +
                    ".4: Pull Weeds...............................\n" +
                    ".............................................\n" +
                    ".............................................\n");

            navInd = myObj.nextLine();  // Read user input
            if(!(navInd.equals("1") ||navInd.equals("2")  ||navInd.equals("3")  ||navInd.equals("4") ||navInd.equals("0"))){
                System.out.println("Enter Valid Option (ex. 1, 2, 3, ...)");
            }else{
                return navInd;
            }
        }

    }

    public String selectFriendMenu(ArrayList<Friend> friends){
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String navInd;
        String[] menuFriendList = new String[]{"<Unknown Friend>","<Unknown Friend>","<Unknown Friend>","<Unknown Friend>","<Unknown Friend>" };
        for(int i = 0; i < friends.size(); i ++){
            menuFriendList[i] = friends.get(i).getName();
        }
        while(true) {
            System.out.println("\n///////////////// HAPPY GO FARMING /////////////////\n" +
                    "........Select a Friend.......0: Prev. Menu\n" +
                    ".1: "+ menuFriendList[0] +" \n" +
                    ".2: "+ menuFriendList[1] +" \n" +
                    ".3: "+ menuFriendList[2] +" \n" +
                    ".4: "+ menuFriendList[3] +" \n" +
                    ".5: "+ menuFriendList[4] +" \n");

            navInd = myObj.nextLine();  // Read user input
            if(!(navInd.equals("1") ||navInd.equals("2")  ||navInd.equals("3")  ||navInd.equals("4") ||navInd.equals("5")||navInd.equals("0"))){
                System.out.println("Enter Valid Option (ex. 1, 2, 3, ...)");
            }else if( !(navInd.equals("0")) && menuFriendList[Integer.parseInt(navInd)-1].equals( "<Unknown Friend>")){
                System.out.println("Select Known Friend");
            }else{
                break;
            }
        }
        return navInd;
    }


    public String ManageFriendsMenu(Friend fren){
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String navInd;
        while(true) {
            System.out.println("\n///////////////// HAPPY GO FARMING /////////////////\n" +
                    "........Interact With " + fren.getName()+ "...........0: Prev. Menu\n" +
                    ".1: Talk.....................................\n" +
                    ".2: Delegate Job.............................\n" +
                    ".............................................\n" +
                    ".............................................\n" +
                    ".............................................\n" +
                    ".............................................\n");

            navInd = myObj.nextLine();  // Read user input
            if(!(navInd.equals("1") ||navInd.equals("2")  ||navInd.equals("3")|| navInd.equals("0"))){
                System.out.println("Enter Valid Option (ex. 1, 2, 3, ...)");
            }else{
                break;
            }
        }
        return navInd;
    }

    public String DelegateJobMenu(Friend fren){
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String navInd;
        while(true) {
            System.out.println("\n///////////////// HAPPY GO FARMING /////////////////\n" +
                    "........Delegate Job to " + fren.getName()+ "...........0: Prev. Menu\n" +
                    "......(each action costs 1 energy point).....\n" +
                    ".1: Water plot...............................\n" +
                    ".2: Plant seed...............................\n" +
                    ".3: Harvest plot.............................\n" +
                    ".4: Pull Weeds ..............................\n" +
                    ".5: Fundraise................................\n" +
                    ".............................................\n");

            navInd = myObj.nextLine();  // Read user input
            if(!(navInd.equals("1") ||navInd.equals("2")  ||navInd.equals("3")  ||navInd.equals("4") ||navInd.equals("5")||navInd.equals("0"))){
                System.out.println("Enter Valid Option (ex. 1, 2, 3, ...)");
            }else{
                break;
            }
        }
        return navInd;
    }

    public String enterMarketMenu(){
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String navInd;
        while(true) {
            System.out.println("\n///////////////// HAPPY GO FARMING /////////////////\n" +
                    "........Welcome to the Market!........0: Prev. Menu\n" +
                    "......(each action costs 1 energy point).....\n" +
                    "....Buy Goods.................Sell Goods...........\n" +
                    ".1: Seeds....................4: Produce............\n" +
                    ".2: Tools....................5: Seeds..............\n" +
                    ".3: Garden Plots...................................\n" +
                    "...................................................\n" +
                    ".6: Fundraise......................................\n");

            navInd = myObj.nextLine();  // Read user input
            if(!(navInd.equals("1") ||navInd.equals("2")  ||navInd.equals("3")  ||navInd.equals("4") ||navInd.equals("5")||navInd.equals("0")||navInd.equals("6"))){
                System.out.println("Enter Valid Option (ex. 1, 2, 3, ...)");
            }else{
                break;
            }
        }
        return navInd;
    }

    public void checkStats(Game game){
        System.out.println("\n///////////////// HAPPY GO FARMING /////////////////\n" +
                    "Stats.........................................\n" +
                    "Day: " +game.getDay() +" .......................................\n" +
                    "Plots: " +game.plots.size() + " .....................................\n" +
                    "Money: " +game.player.getMoney() + " ..................................\n" +
                    "Energy Remaining: " + game.player.getEnergyPoints()+ " .........................\n" +
                    "Friends: " +game.Friends.size() + " ...................................\n" +
                    "..............................................\n");
    }



}
