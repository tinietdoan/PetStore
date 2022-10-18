package Events;
import Friends.*;

//The Event System is a decorator pattern
//This object has the information and functions used by the main game object to simulated the layered events
//using the decorator pattern.



public class EventGetter {
//    Event e1; //Friend Comes by to see if you need help
//    Event e2; //Sale At the Marketplace for 3 days.
//    Event e3; //Drought un-waters plants
//    Event e4; //Someone is impressed with your fundraising, becomes friend
//    Event e5; //Someone is impressed with your produce selling, becomes friend.
//    Event e6; //Some dang kids steal produce. One feels bad an becomes friend.
//    Event e7; //Swarm of Hungry Locusts clear out garden plot.
//    Event e8; //Heavy Rains water all plots for two days.
boolean triggered[];



    public EventGetter(){
        this.triggered = new boolean[]{false,false,false,false,false,false,false,false};
    }


    public Event getE1(Friend fren) {
        Event base = new baseEvent();
        friendEvent e1 = new friendEvent(base, fren);
        e1.setID(1);
        this.triggered[0] = true;
        return e1;
    }

    public Event getE2() {
        Event base = new baseEvent();
        modifierEvent e2 = new modifierEvent(base, "Market Prices", .5, 3);
        e2.setID(2);
        this.triggered[1] = true;
        return e2;
    }

    public Event getE3(){
        Event base = new baseEvent();
        oneOffEvent e3 = new oneOffEvent(base, 0.0, "Water");
        e3.setID(3);
        this.triggered[2] = true;
        return e3;
    }

    public Event getE4(Friend fren) {
        Event base = new baseEvent();
        friendEvent e1 = new friendEvent(base, fren);
        oneOffEvent e4 = new oneOffEvent(e1, 30.0, "Money");
        e4.setID(4);
        this.triggered[3] = true;
        return e4;
    }

    public Event getE5(Friend fren) {
        Event base = new baseEvent();
        friendEvent e1 = new friendEvent(base, fren);
        modifierEvent e5 = new modifierEvent(e1,"Market Prices", 2.0 , 5);
        e5.setID(5);
        this.triggered[4] = true;
        return e5;
    }

    public Event getE6(Friend fren) {
        Event base = new baseEvent();
        friendEvent e1 = new friendEvent(base, fren);
        oneOffEvent e6 = new oneOffEvent(e1, 0.0, "GardenPlotProduce");
        e6.setID(6);
        this.triggered[5] = true;
        return e6;
    }


    public Event getE7() {
        Event base = new baseEvent();
        oneOffEvent e7 = new oneOffEvent(base, 0.0, "GardenPlot");
        e7.setID(7);
        this.triggered[6] = true;
        return e7;
    }

    public Event getE8() {
        Event base = new baseEvent();
        oneOffEvent e8 = new oneOffEvent(base, 1.0, "Water");
        e8.setID(8);
        this.triggered[7] = true;
        return e8;
    }

    public boolean[] getTriggered(){
        return triggered;
    }



}
