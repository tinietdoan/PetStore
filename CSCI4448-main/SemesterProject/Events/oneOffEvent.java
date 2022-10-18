package Events;


//The Event System is a decorator pattern
//The one off event is a layer that can be wrapped around other events to add a one off effect

public class oneOffEvent extends EventDecorator{

    Event event;
    Double change;
    String subject;

    public oneOffEvent(Event base, Double change, String subject){
        this.event = base;
        this.change = change;
        this.subject = subject;
    }

    public String getDesc(){
        return event.getDesc() + "One off event! Changing the " + subject + " by " + change + ". ";
    }

    public Double getChange(){
        return change;
    }

    public String getOneOffSubject(){
        return subject;
    }

    public Event getBaseEvent(){
        return event;
    }
}
