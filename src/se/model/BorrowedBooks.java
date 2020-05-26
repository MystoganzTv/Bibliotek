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
public class BorrowedBooks {
    
    private int id ;
    private int bookId;
    private int libraryCardId;
    private Date startDate;
    private Date returnDate;

    public BorrowedBooks(int id, int bookId, int libraryCardId, Date startDate, Date returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.libraryCardId = libraryCardId;
        this.startDate = startDate;
        this.returnDate = returnDate;
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
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

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    
     @Override
    public String toString() {
        return bookId + "";
    }
    
}
