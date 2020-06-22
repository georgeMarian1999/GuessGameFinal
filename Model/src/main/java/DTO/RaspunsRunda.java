package DTO;

import java.io.Serializable;

public class RaspunsRunda implements Serializable {
    private int idJuc;
    private String cuvGhicit;
    private int Punctaj;
    private int nrRunda;
    public RaspunsRunda(String cuvGhicit,int idJuc,int p,int nr){
        this.cuvGhicit=cuvGhicit;
        this.idJuc=idJuc;
        this.Punctaj=p;
        this.nrRunda=nr;
    }

    public void setNrRunda(int nrRunda) {
        this.nrRunda = nrRunda;
    }

    public int getNrRunda() {
        return nrRunda;
    }

    public int getPunctaj() {
        return Punctaj;
    }

    public void setPunctaj(int punctaj) {
        Punctaj = punctaj;
    }

    public String getCuvGhicit() {
        return cuvGhicit;
    }

    public int getIdJuc() {
        return idJuc;
    }
}
