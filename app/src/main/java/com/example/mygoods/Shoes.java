package com.example.mygoods;

public class Shoes {

    private int id;
    private String model;
    private int size;
    private String color;
    private int quantity;
    private double price;
    private String date;
    private String time;

    public Shoes(int id, String model, int size, String color, int quantity,double price) {
        this.id = id;
        this.model = model;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
        this.price = price;
    }

    public Shoes(String model, int size, String color, int quantity,double price) {
        this.model = model;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
        this.price = price;
    }

    public Shoes(String model, int size, String color, int quantity, double price, String date, String time) {
        this.model = model;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
        this.time = time;
    }

    public Shoes(int id,String model, int size, String color, int quantity, double price, String date, String time) {
        this.id = id;
        this.model = model;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
