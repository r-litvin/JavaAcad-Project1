package com.engeto.restaurant;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Order {
    private int tableNumber;
    private int dishId;
    private LocalDateTime orderedTime;
    private LocalDateTime fulfilmentTime;
    private boolean isPaid;

    public Order(int tableNumber, int dishId){
        this.tableNumber = tableNumber;
        this.dishId = dishId;
        this.orderedTime = LocalDateTime.now();
    }

    public Order(int tableNumber, int dishId, LocalDateTime orderedTime, LocalDateTime fulfilmentTime, boolean isPaid){
        this.tableNumber = tableNumber;
        this.dishId = dishId;
        this.orderedTime = orderedTime;
        this.setFulfilmentTime(fulfilmentTime);
        this.setPaid(isPaid);
    }

    //region: getters and setters


    public int getTableNumber() {
        return tableNumber;
    }

    public int getDishId() {
        return dishId;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
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
        lineToWrite += orderedTime + separator;
        lineToWrite += fulfilmentTime + separator;
        lineToWrite += isPaid;
        return lineToWrite;
    }

}
