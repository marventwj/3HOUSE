package com.example.marven.a3house;

/**
 * Created by Marven on 15-10-16.
 */
public class Property {
    Boolean forRent;    //status true = for rent , status false = for sale, or can use ENUM
    String details;     //details of the property (or can create an object called property details with get string methods (e.g unit,price,etc)
    //need add an attribute for property image (maybe an arraylist of images)
    String ownerName;//need add an attribute for owner image and name (maybe an object "owner" consisting of string name and owner's image)

    public Property(Boolean forRent, String details, String ownerName) {
        this.forRent = forRent;
        this.details = details;
        this.ownerName = ownerName;
    }

    public Boolean getRentStatus() {
        return forRent;
    }

    public String getDetails() {
        return details;
    }

    public String getOwnerName() {
        return ownerName;
    }

}
