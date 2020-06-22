package DTO;

import java.io.Serializable;

public class CuvintePropuse implements Serializable {
    private int idJuc;
    private String cuvantul;

    public CuvintePropuse(int id,String cuv){
        this.idJuc=id;
        this.cuvantul=cuv;
    }
    public int getIdJuc() {
        return idJuc;
    }

    public String getCuvantul() {
        return cuvantul;
    }

    public void setCuvantul(String cuvantul) {
        this.cuvantul = cuvantul;
    }

    public void setIdJuc(int idJuc) {
        this.idJuc = idJuc;
    }

}
