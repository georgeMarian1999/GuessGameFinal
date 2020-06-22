package DTO;

import java.io.Serializable;

public class JucatorDTO implements Serializable {
    private String username;
    private String password;

    public JucatorDTO(String user,String pass){
        this.username=user;
        this.password=pass;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
