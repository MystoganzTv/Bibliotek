/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.model;

/**
 *
 * @author Enrique
 */
public class Seminarium {
    
    private int id; 
    
    private String title;
    
    private String Speaker;
    
    private String startDate;
    
    private String endDate;
    
    private int countVisitor;
    
    private String seminariumDescription;
    
    private String programDescription;

    public Seminarium(int id, String title, String Speaker, String startDate, String endDate, int countVisitor, String seminariumDescription, String programDescription) {
        this.id = id;
        this.title = title;
        this.Speaker = Speaker;
        this.startDate = startDate;
        this.endDate = endDate;
        this.countVisitor = countVisitor;
        this.seminariumDescription = seminariumDescription;
        this.programDescription = programDescription;
    }

    public Seminarium(String title, String Speaker, String startDate, String endDate, int countVisitor, String seminariumDescription, String programDescription) {
        this.title = title;
        this.Speaker = Speaker;
        this.startDate = startDate;
        this.endDate = endDate;
        this.countVisitor = countVisitor;
        this.seminariumDescription = seminariumDescription;
        this.programDescription = programDescription;
    }

    public Seminarium() {
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

    public String getSpeaker() {
        return Speaker;
    }

    public void setSpeaker(String Speaker) {
        this.Speaker = Speaker;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getCountVisitor() {
        return countVisitor;
    }

    public void setCountVisitor(int countVisitor) {
        this.countVisitor = countVisitor;
    }

    public String getSeminariumDescription() {
        return seminariumDescription;
    }

    public void setSeminariumDescription(String seminariumDescription) {
        this.seminariumDescription = seminariumDescription;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
    }
    
    
    
}
