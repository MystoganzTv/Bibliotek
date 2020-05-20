/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.model;

/**
 *
 * @author enrique & simon
 */
public class Seminar {
    
    private int id; 
    
    private String title;
    
    private String Speaker;
    
    private String location;
    
    private String startDate;
    
    private int countVisitor;
    
    private String seminariumDescription;
    
    private String programDescription;

    public Seminar(int id, String title, String Speaker, String location, String startDate, int countVisitor, String seminariumDescription, String programDescription) {
        this.id = id;
        this.title = title;
        this.Speaker = Speaker;
        this.location = location;
        this.startDate = startDate;
        this.countVisitor = countVisitor;
        this.seminariumDescription = seminariumDescription;
        this.programDescription = programDescription;
    }

    public Seminar(String title, String speaker, String location, String startDate, int countVisitor, String seminariumDescription, String programDescription) {
        this.title = title;
        this.Speaker = speaker;
        this.location = location;
        this.startDate = startDate;
        this.countVisitor = countVisitor;
        this.seminariumDescription = seminariumDescription;
        this.programDescription = programDescription;
    }

    public Seminar() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
