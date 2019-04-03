/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author SENA
 */
public class UserData {
    private String Username;
    private String Password;
    public UserData(String Username, String Password) {
        this.Username = Username;
        this.Password = Password;
    }
    
    public void setUsername(String Username) {
        this.Username = Username;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
    
    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }
    
}
