package Events;

//The Event System is a decorator pattern
//The base event is a black event to be built upon by the decorator functionality

public class baseEvent extends Event {
    String description = "Event in which: ";


    public String getDesc(){
        return description;
    }

    public Event getBaseEvent(){
        return this;
    }


}