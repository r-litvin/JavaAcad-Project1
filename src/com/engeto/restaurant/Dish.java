package com.engeto.restaurant;

import java.math.BigDecimal;

public class Dish {
    private static int dishIdStart = 0;
    private final int dishId;
    private String title;
    private BigDecimal price;
    private int preparationTime;
    private String image;

    public Dish(String title, BigDecimal price, int preparationTime, String image) {
        this.dishId = dishIdStart++;
        this.title = title;
        this.price = price;
        this.setPreparationTime(preparationTime);
        this.image = image;
    }
    //this constructor for loading Dishes from file
    public Dish(int dishId, String title, BigDecimal price, int preparationTime, String image) {
        this.dishId = dishId;
        this.title = title;
        this.price = price;
        this.setPreparationTime(preparationTime);
        this.image = image;
    }

    public Dish(String title, BigDecimal price, int preparationTime) {
        this(title, price, preparationTime, "blank");
    }

    //region: getters and setters
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

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    //endregion

}
