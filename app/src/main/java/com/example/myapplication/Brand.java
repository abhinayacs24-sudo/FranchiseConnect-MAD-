package com.example.myapplication;

public class Brand {
    private String name;
    private String category;
    private String investment;
    private int logoResId;

    public Brand(String name, String category, String investment, int logoResId) {
        this.name = name;
        this.category = category;
        this.investment = investment;
        this.logoResId = logoResId;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getInvestment() { return investment; }
    public int getLogoResId() { return logoResId; }
}
