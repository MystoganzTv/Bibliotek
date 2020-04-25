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
public class LibraryCards {
    private int id;
    private int guestId;
    private String notes;
    private String category;
    private int entry;
    
    
    private String fullname;

    public LibraryCards(int id, int guestId, String notes, String category, int entry) {
        this.id = id;
        this.guestId = guestId;
        this.notes = notes;
        this.category = category;
        this.entry = entry;
    }
    
    public LibraryCards(String fullname, int guestId) {
        this.id = id;
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getEntry() {
        return entry;
    }

    public void setEntry(int entry) {
        this.entry = entry;
    }
    
}
