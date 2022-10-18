package Events;
import Friends.*;

//The Event System is a decorator pattern
//The friend event is a layer that can be wrapped around other events to add a friend


public class friendEvent extends EventDecorator{
    Event event;
    Friend friend;

    public friendEvent(Event base, Friend fr){
        this.event = base;
        this.friend = fr;
    }

    public String getDesc(){
        return event.getDesc() + "Friend " + friend.getName() + " joined the garden. ";
    }

    public Friend getFriend(){
        return friend;
    }

    public Event getBaseEvent(){
        return event;
    }


}
