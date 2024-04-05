package edu.tcu.cs.superfrogscheduler.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.*;

// customer can request a SuperFrog appearance by submitting a SuperFrogAppearanceRequest
// SuperFrogAppearanceRequest includes event-related information
public class SuperFrogAppearanceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer requestId;

    private String contactFirstName;
    private String contactLastName;
    private String phoneNumber;
    private String email;
    private EventType eventType;
    private String eventTitle;
    private String nameOfOrg;
    private String address;
    private String isOnTCUCampus;
    private String specialInstructions;
    private String expenses;
    private String outsideOrgs;
    private String description;

    public String getContactFirstName(){
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName){
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName(){
        return contactLastName;
    }

    public void setContactLastName(String contactLastName){
        this.contactLastName = contactLastName;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public EventType getEventType(){
        return eventType;
    }

    public void setEventType(EventType eventType){
        this.eventType = eventType;
    }

    public String getEventTitle(){
        return eventTitle;
    }

    public void setEventTitle(String eventTitle){
        this.eventTitle = eventTitle;
    }

    public String getNameOfOrg(){
        return nameOfOrg;
    }

    public void setNameOfOrg(String nameOfOrg){
        this.nameOfOrg = nameOfOrg;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getIsOnTCUCampus(){
        return isOnTCUCampus;
    }

    public void setIsOnTCUCampus(String isOnTCUCampus){
        this.isOnTCUCampus = isOnTCUCampus;
    }

    public String getSpecialInstructions(){
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions){
        this.specialInstructions = specialInstructions;
    }

    public String getExpenses(){
        return expenses;
    }

    public void setExpenses(String expenses){
        this.expenses = expenses;
    }

    public String getOutsideOrgs(){
        return outsideOrgs;
    }

    public void setOutsideOrgs(String outsideOrgs){
        this.outsideOrgs = outsideOrgs;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public SuperFrogAppearanceRequest(){

    }

    public SuperFrogAppearanceRequest(Integer requestId, String contactFirstName, String contactLastName, String phoneNumber, String email, EventType eventType, String eventTitle, String nameOfOrg, String address, String isOnTCUCampus, String specialInstructions, String expenses, String outsideOrgs, String description){
        this.requestId = requestId;
        this.contactFirstName = contactFirstName;
        this.contactLastName = contactLastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.eventType = eventType;
        this.eventTitle = eventTitle;
        this.nameOfOrg = nameOfOrg;
        this.address = address;
        this.isOnTCUCampus = isOnTCUCampus;
        this.specialInstructions = specialInstructions;
        this.expenses = expenses;
        this.outsideOrgs = outsideOrgs;
        this.description = description;
    }
}
