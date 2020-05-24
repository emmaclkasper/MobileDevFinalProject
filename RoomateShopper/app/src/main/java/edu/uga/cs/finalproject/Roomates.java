package edu.uga.cs.finalproject;

import java.util.ArrayList;

public class Roomates {

    private String name;
    private String amountSpent;
    private ArrayList<Item> itemsPurchased;

    public Roomates(){

    }

    public Roomates(String name, String amountSpent) {
        this.name = name;
        this.amountSpent = amountSpent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(String amountSpent) {
        this.amountSpent = amountSpent;
    }

    public ArrayList<Item> getItemsPurchased() {
        return itemsPurchased;
    }

    public void setItemsPurchased(ArrayList<Item> itemsPurchased) {
        this.itemsPurchased = itemsPurchased;
    }
}
