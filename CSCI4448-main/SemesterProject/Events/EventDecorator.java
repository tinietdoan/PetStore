package Events;

//The Event System is a decorator pattern

public abstract class EventDecorator extends Event {
    public abstract String getDesc();
}