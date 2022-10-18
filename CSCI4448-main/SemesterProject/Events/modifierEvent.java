package Events;



//The Event System is a decorator pattern
//The modifier event is a layer that can be wrapped around other events to add a lasting effect

public class modifierEvent extends EventDecorator{


    Event event;
    String subject;
    Double modifier;
    int duration;

    public modifierEvent(Event base, String subject, Double mod, int duration){
        this.event = base;
        this.subject = subject;
        this.modifier = mod;
        this.duration = duration;
    }

    public String getDesc(){
        return event.getDesc() + "Adding a modifier of " + modifier + " to " + subject + " for " + duration + " days. ";
    }

    public Double getModifier(){
        return modifier;
    }

    public String getModSubject(){
        return subject;
    }

    public Event getBaseEvent(){
        return event;
    }

    public int getDur(){
        return duration;
    }

    public void decrDuration(){
        duration --;
    }
}
