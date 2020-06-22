package Model;

import java.io.Serializable;

public class Raspuns implements Serializable {
    private String usernameJuc;
    private int idPropus;
    private String cuvGhicit;

    public Raspuns(){

    }
    public Raspuns(String user,int idGhicit,String cuv){
        this.usernameJuc=user;
        this.idPropus=idGhicit;
        this.cuvGhicit=cuv;

    }

    public void setCuvGhicit(String cuvGhicit) {
        this.cuvGhicit = cuvGhicit;
    }

    public String getCuvGhicit() {
        return cuvGhicit;
    }

    public int getIdPropus() {
        return idPropus;
    }

    public void setIdPropus(int idPropus) {
        this.idPropus = idPropus;
    }

    public String getUsernameJuc() {
        return usernameJuc;
    }

    public void setUsernameJuc(String usernameJuc) {
        this.usernameJuc = usernameJuc;
    }


}
