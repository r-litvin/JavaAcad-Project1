package com.engeto.restaurant;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

public class Order {
    private int tableNumber;
    private int dishId;
    private int dishCount;
    private LocalDateTime orderedTime;
    private LocalDateTime fulfilmentTime;
    private boolean isPaid;

    public Order(int tableNumber, int dishId, int dishCount){
        this.tableNumber = tableNumber;
        this.dishId = dishId;
        this.dishCount = dishCount;
        this.orderedTime = LocalDateTime.now();
    }

    //region: getters and setters


    public int getTableNumber() {
        return tableNumber;
    }

    public int getDishId() {
        return dishId;
    }

    public int getDishCount() {
        return dishCount;
    }

    public void setDishCount(int dishCount) {
        this.dishCount = dishCount;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }
    /* Only for testing sort method! */
    public void setOrderedTime(LocalDateTime orderedTime) {
        this.orderedTime = orderedTime;
    }

    public LocalDateTime getFulfilmentTime() {
        return fulfilmentTime;
    }

    public void setFulfilmentTime(LocalDateTime fulfilmentTime) {
        this.fulfilmentTime = fulfilmentTime;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
    //endregion

    public String stringToFile(String separator) {
        String lineToWrite = "";
        lineToWrite += tableNumber + separator;
        lineToWrite += dishId + separator;
        lineToWrite += dishCount + separator;
        lineToWrite += orderedTime + separator;
        lineToWrite += fulfilmentTime + separator;
        lineToWrite += isPaid;
        return lineToWrite;
    }

    @Override
    public String toString() {
        return this.stringToFile("; ");
    }

    //not used now?
    public int compareTo(Order otherOrder) {
        return this.getOrderedTime().compareTo(otherOrder.getOrderedTime());
    }

    public int fulfillmentTimeMinutes(){
        return (int) ChronoUnit.MINUTES.between(this.getOrderedTime(), this.getFulfilmentTime());
    }
}
