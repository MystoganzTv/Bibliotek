/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.model;

/**
 *
 * @author adde
 */
/**
 * 
 * Login with email and password
 */
public class Admin {
    private int id;
    
    private String firstName;
    
    private String lastName;
    
    private String personId;
   
    private String password;
    
    private String email;

    public Admin(String firstName, String lastName, String personId, String password, int id, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personId = personId;
        this.password = password;
        this.id = id;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
}


