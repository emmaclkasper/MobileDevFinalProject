package edu.uga.cs.finalproject;

public class Item {
    private String itemName;
    private String price;
    private String id;
    private String date;
    private String roommateName;

    public Item(){
    }


    public Item(String itemName, String price, String roommateName){
        this.itemName = itemName;
        this.price = price;
        this.roommateName = roommateName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public void setPrice(String price) { this.price = price; }

    public String getPrice() {return price; }

    public String getRoommateName() { return roommateName; }

    public void setRoommateName(String roommateName) { this.roommateName = roommateName; }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

}
