package edu.uga.cs.finalproject;

public class Details {

    private String itemName;
    private String date;
    private String roommateName;
    private String price;

    public Details() {

    }

    public Details(String itemName, String date, String roommateName, String price) {
        this.itemName = itemName;
        this.date = date;
        this.roommateName = roommateName;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoommateName() {
        return roommateName;
    }

    public void setRoommateName(String roommateName) {
        this.roommateName = roommateName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
