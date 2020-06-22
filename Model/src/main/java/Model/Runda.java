package Model;

import java.io.Serializable;

public class Runda implements Serializable {

    private int JucatorId;
    private int JocId;
    private int idJucGhicit;
    private String cuvGhicit;
    private int Punctaj;
    private int NrRunda;

    public Runda(){

    }
    public Runda(int jucatorId,int jocId,int idJucGhicit1,String cuvGhicit1,int punc,int nr){
        this.JucatorId=jucatorId;
        this.JocId=jocId;
        //this.cuvPropus=cuvPropus;
        this.idJucGhicit=idJucGhicit1;
        this.cuvGhicit=cuvGhicit1;
        this.Punctaj=punc;
        this.NrRunda=nr;
    }

    public int getIdJucGhicit() {
        return idJucGhicit;
    }

    public void setIdJucGhicit(int idJucGhicit) {
        this.idJucGhicit = idJucGhicit;
    }

    public String getCuvGhicit() {
        return cuvGhicit;
    }

    public void setCuvGhicit(String cuvGhicit) {
        this.cuvGhicit = cuvGhicit;
    }

    public int getJocId() {
        return JocId;
    }

    public int getJucatorId() {
        return JucatorId;
    }

    public int getNrRunda() {
        return NrRunda;
    }

    public int getPunctaj() {
        return Punctaj;
    }



    public void setJocId(int jocId) {
        JocId = jocId;
    }

    public void setJucatorId(int jucatorId) {
        JucatorId = jucatorId;
    }


    public void setNrRunda(int nrRunda) {
        NrRunda = nrRunda;
    }

    public void setPunctaj(int punctaj) {
        Punctaj = punctaj;
    }



}
