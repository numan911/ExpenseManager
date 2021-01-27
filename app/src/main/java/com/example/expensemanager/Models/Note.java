package com.example.expensemanager.Models;

public class Note {
    private String title;
    private String description;
    private String amount;
    private String date;

    public Note() {
        //public no-arg constructor needed
    }

    public Note(String title, String description, String amount, String date) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}
