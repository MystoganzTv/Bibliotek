/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.model;

/**
 *
 * @author danny
 */
public class Booking {
    private int seminarId;
    private int libraryCardId;
    private int id;

    public Booking(int seminarId, int libraryCardId, int id) {
        this.seminarId = seminarId;
        this.libraryCardId = libraryCardId;
        this.id = id;
    }

    public Booking(int i) {
        this.id = i;
    }
    
    
    
    public int getSeminarId() {
        return seminarId;
    }

    public void setSeminarId(int seminarId) {
        this.seminarId = seminarId;
    }

    public int getLibraryCardId() {
        return libraryCardId;
    }

    public void getLibraryCardId(int libraryCardId) {
        this.libraryCardId = libraryCardId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
}
