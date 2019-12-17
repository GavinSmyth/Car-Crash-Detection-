package com.example.carcrashdetection;

public class Event {

    String carMake;
    String carType;
    String CarYear;


    public Event() {
    }

    public Event(String carMake, String carType, String carYear) {
        this.carMake = carMake;
        this.carType = carType;
        CarYear = carYear;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarYear() {
        return CarYear;
    }

    public void setCarYear(String carYear) {
        CarYear = carYear;
    }
}
