import java.util.*;

public class Event() implements Comparable<Event>{

    /*Events:   I1C = Inspector 1 has finished processing
                I2C = Inspector 2 has finished processing
                WC  = A workstation has finished processing
     */
    private static enum eventType {I1C, I2C, WC}
    private int time;
    private eventType type;
    private Object subject; //Variable type object to accept both inspector and workstation objects

    public Event(int time, eventType type, Object subject){
        this.time = time;
        this.type = type;
        this.subject = subject;
    }

    @Override
    public int compareTo(Event ev) {
        return this.getTime().compareTo(ev.getTime());
    }

    public int getTime(){
        return time;
    }

    public void setTime(int time){
        this.time = time;
    }

    public eventType getType(){
        return type;
    }

    public void setType(eventType type){
        this.type = type;
    }

    public Object getSubject(){
        return subject;
    }

    public void setSubject(Object subject){
        this.subject = subject;
    }
}