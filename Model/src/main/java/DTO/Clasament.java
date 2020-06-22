package DTO;

import java.io.Serializable;

public class Clasament implements Serializable {
    private String username;
    private int punctaj;
    public Clasament(String user,int p){
        this.username=user;
        this.punctaj=p;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPunctaj(int punctaj) {
        this.punctaj = punctaj;
    }

    public int getPunctaj() {
        return punctaj;
    }
}
