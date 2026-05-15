package com.example.medicalstore.medicine;

public abstract class Medicine {

    private String medId;
    private String name;
    private String category;
    private double price;
    private int stock;
    private String description;

    public Medicine(String medId, String name, String category, double price, int stock,String description) {
        this.medId    = medId;
        this.name     = name;
        this.category = category;
        this.price    = price;
        this.stock    = stock;
        this.description  = description;
    }

    public String getMedId(){
        return medId;
    }
    public String getName(){
        return name;
    }
    public String getCategory() {
        return category;
    }
    public double getPrice(){
        return price;
    }
    public int getStock(){
        return stock;
    }
    public String getDescription()    {
        return description; }

    public void setPrice(double price) {
        this.price = price;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public abstract String getDisplayInfo();

    public String toFileString() {
        return medId+","+name+","+category+","+price+","+stock+","+getDisplayInfo() + "," + description;
    }

    public static Medicine fromFileString(String line) {
        String[] p = line.split(",",7);
        String desc = p.length > 6 ? p[6] : "No description available.";
        return new OTCMedicine(p[0], p[1], p[2],
                Double.parseDouble(p[3]),
                Integer.parseInt(p[4]));
    }
}
