/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.model;

import java.sql.Date;

/**
 *
 * @author danny
 */
public class BorrowEBooks {
    private int id ;
    private int eBookId;
    private int libraryCardId;
    private Date startDate;

    public BorrowEBooks(int id, int eBookId, int libraryCardId, Date startDate) {
        this.id = id;
        this.eBookId = eBookId;
        this.libraryCardId = libraryCardId;
        this.startDate = startDate;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int geteBookId() {
        return eBookId;
    }

    public void seteBookId(int eBookId) {
        this.eBookId = eBookId;
    }

    public int getLibraryCardId() {
        return libraryCardId;
    }

    public void setLibraryCardId(int libraryCardId) {
        this.libraryCardId = libraryCardId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    
}
