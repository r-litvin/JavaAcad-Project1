package com.engeto.restaurant;

import java.math.BigDecimal;

public class Dish {
    private static int dishIdStart = 0;
    private int dishId;
    private String title;
    private BigDecimal price;
    private int preparationTime;
    private String image;

    public Dish(String title, BigDecimal price, int preparationTime, String image) throws RestaurantException  {
        this.dishId = dishIdStart++;
        this.title = title;
        this.price = price;
        this.setPreparationTime(preparationTime);
        this.image = image;
    }

    /* constructor for loading Dishes from file */
    public Dish(int dishId, String title, BigDecimal price, int preparationTime, String image)  throws RestaurantException{
        this.dishId = dishId;
        this.title = title;
        this.price = price;
        this.setPreparationTime(preparationTime);
        this.image = image;
    }

    public Dish(String title, BigDecimal price, int preparationTime) throws RestaurantException {
        this(title, price, preparationTime, "blank");
    }

    //region: getters and setters
    public void setDishIdStart(int idStart){
        dishIdStart = idStart;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) throws RestaurantException {
        if (preparationTime <= 0){
            throw new RestaurantException("Error setting up new Dish: preparation time has to be more than 0 minutes!");
        }
        this.preparationTime = preparationTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDishId() {
        return dishId;
    }

    //endregion

    public String stringToFile(String separator){
        String lineToWrite = "";
        lineToWrite += this.getDishId()+separator;
        lineToWrite += this.getTitle()+separator;
        lineToWrite += this.getPrice()+separator;
        lineToWrite += this.getPreparationTime()+separator;
        lineToWrite += this.getImage();
        return lineToWrite;
    }

    public String toString(){
        return stringToFile("; ");
    }

}
