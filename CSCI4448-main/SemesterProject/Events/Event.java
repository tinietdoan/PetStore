package Events;

//The Event System is a decorator pattern
//This is the abstract class that both the base event, and the event interface extend.


public abstract class Event {
    String description = "UnknownEvent...: ";
    int EventID;

    public String getDesc(){
        System.out.println(description);
        return description;
    }

    public void setID(int ID){
        EventID = ID;
    }

    public int getID(){
        return EventID;
    }

    public abstract Event getBaseEvent();



}