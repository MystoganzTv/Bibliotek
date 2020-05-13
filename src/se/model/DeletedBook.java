/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.model;

/**
 *
 * @author enrique
 */
public class DeletedBook {

    private int id;

    private String title;
    private String author;
    private String bookType;
    private String isbn;
    private double purchasePrice;
    private String publisher;
    private String placement;
    private String category;
    private String notes;
    
    private int inStock;

    private String desc;
    

    public DeletedBook(int id, String title, String author, String bookType,String isbn, String purchasePrice, String category, String publisher, String placement, String notes) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.bookType = bookType;        
        this.isbn = isbn;
        this.purchasePrice = purchasePrice;
        this.category = category;
        this.publisher = publisher;
        this.placement = placement;
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public DeletedBook() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }   

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

}
