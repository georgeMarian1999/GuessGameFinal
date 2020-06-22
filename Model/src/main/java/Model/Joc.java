package Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "Joc")
@Table(name = "Joc")
public class Joc implements Serializable {
    @Id
    @Column(name = "id")
    private int id;
    public Joc(){

    }
    public Joc(int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
