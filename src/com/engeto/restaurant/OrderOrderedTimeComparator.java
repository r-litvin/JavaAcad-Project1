package com.engeto.restaurant;
import java.util.Comparator;

public class OrderOrderedTimeComparator implements Comparator<Order> {

    public int compare(Order order1, Order order2){
        return order1.getOrderedTime().compareTo(order2.getOrderedTime());
    }
}
