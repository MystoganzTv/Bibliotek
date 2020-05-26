/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.model;

import java.util.Date;

/**
 *
 * @author annaz
 */
public class E_Books {
    
    private int id;
    
    private String title;
    
    private String author;
    
    private String isbn;
    
    private String publisher;
    
    private double purchase_price;
    
    private String category;
    
    private String desc;
    
    private Date date;
    
    public E_Books(int id, String title, String author, String isbn, String publisher, double purchase_price, String category, String desc) {

        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.purchase_price = purchase_price;
        this.category = category;
        this.desc = desc;
       
    }

    public E_Books(String title, String author, String isbn, String publisher, double purchase_price, String category, String desc) {

       
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.purchase_price = purchase_price;
        this.category = category;
        this.desc = desc;
       
    }

    public E_Books(String title, String author, String isbn, String publisher, double purchase_price, String category, String desc, Date date) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.purchase_price = purchase_price;
        this.category = category;
        this.desc = desc;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
    
    public E_Books() {
        
    }
    

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(double purchase_price) {
        this.purchase_price = purchase_price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    
}
