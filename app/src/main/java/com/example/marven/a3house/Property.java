package com.example.marven.a3house;

/**
 * Created by Marven on 15-10-16.
 */
public class Property {
    boolean forRent;    //status true = for rent , status false = for sale, or can use ENUM
    String title;
    String details;     //details of the property (or can create an object called property details with get string methods (e.g title,unit,price,nearby facilities,comments)
    //need add an attribute for property image (maybe an arraylist of images (bitmap object?))
    String ownerName;//need add an attribute for owner profile image and name (maybe an object "owner" consisting of string name and owner's image)
    double lat;
    double lng;

    public Property(boolean forRent, String title, String details, String ownerName , double lat, double lng) {
        this.forRent = forRent;
        this.title = title;
        this.details = details;
        this.ownerName = ownerName;
        this.lat = lat;
        this.lng = lng;
    }

    //need add set methods
    public boolean getRentStatus() {
        return forRent;
    }

    public String getTitle() {return title;}

    public String getDetails() {
        return details;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

}
