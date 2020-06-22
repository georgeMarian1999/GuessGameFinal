package Notification;

import java.io.Serializable;

public class Notification implements Serializable {
    private NotificationType type;
    private String lit;
    public Notification(){

    }
    public Notification(NotificationType t){
        this.type=t;
    }
    public Notification(NotificationType t,int punc){
        this.type=t;

    }
    public Notification(NotificationType t,String l){
        this.type=t;
        this.lit=l;
    }

    public String getLit() {
        return lit;
    }

    public void setLit(String lit) {
        this.lit = lit;
    }



    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }
}
