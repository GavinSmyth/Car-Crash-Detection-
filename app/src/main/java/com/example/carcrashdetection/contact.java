package com.example.carcrashdetection;

public class contact {
    String contactName;
    String contactPhone;
    private boolean isSelected;

    public contact() {
    }

    public contact(String contactName, String contactPhone) {
        this.contactName = contactName;
        this.contactPhone = contactPhone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public boolean isSelected(boolean selected){
        return isSelected= selected;
    }
}
