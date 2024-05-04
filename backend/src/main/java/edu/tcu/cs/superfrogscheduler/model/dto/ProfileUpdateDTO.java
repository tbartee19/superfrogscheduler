package edu.tcu.cs.superfrogscheduler.model.dto;

public class ProfileUpdateDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String physicalAddress;
    private String email;
    private Boolean internationalStudent;
    private String paymentPreference;
    private Boolean isActive;


    @Override
    public String toString() {
        return "ProfileUpdateDTO{" +
               "firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", phoneNumber='" + phoneNumber + '\'' +
               ", physicalAddress='" + physicalAddress + '\'' +
               ", email='" + email + '\'' +
               ", internationalStudent=" + internationalStudent +
               ", paymentPreference='" + paymentPreference + '\'' +
               ", isActive=" + isActive +
               '}';
    }
    
    // Getters and Setters
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getInternationalStudent() {
        return internationalStudent;
    }

    public void setInternationalStudent(Boolean internationalStudent) {
        this.internationalStudent = internationalStudent;
    }

    public String getPaymentPreference() {
        return paymentPreference;
    }

    public void setPaymentPreference(String paymentPreference) {
        this.paymentPreference = paymentPreference;
    }
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}

