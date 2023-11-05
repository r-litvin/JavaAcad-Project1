package com.engeto.restaurant;

import java.time.LocalDate;
import java.util.ArrayList;

public class RestaurantManager {
    private DishBook dishBook;
    private OrderBook orderBook;

    public RestaurantManager(DishBook dishBook, OrderBook orderBook){
        this.dishBook = dishBook;
        this.orderBook = orderBook;
    }

    public int countUnfulfilledOrders(){
        int ordersNotFinished=0;
        for(Order order : orderBook){
            if(order.getFulfilmentTime()==null){
                ordersNotFinished++;
            }
        }
        return ordersNotFinished;
    }

    public double averageFulfillmentTime(){
        int totalFulfilledOrders=0;
        double totalFulfillmentTime=0.0;
        for(Order order : orderBook){
            if(order.getFulfilmentTime() != null){
                totalFulfillmentTime += order.fulfillmentTimeMinutes();
                System.out.println("order: "+order+"| time "+order.fulfillmentTimeMinutes());
                totalFulfilledOrders++;
            }
        }
        if(totalFulfilledOrders==0){
            return 0.0;
        } else {
            return totalFulfillmentTime / (double) totalFulfilledOrders;
        }
    }

    public ArrayList<Dish> dishesOrderedToday(){
        ArrayList<Dish> orderedToday = new ArrayList<>();
        for (Order order : orderBook){
            LocalDate ordered = order.getOrderedTime().toLocalDate();
            if(ordered.equals(LocalDate.now())) {
                if (!orderedToday.contains(dishBook.get(order.getDishId()))) {
                    orderedToday.add(dishBook.get(order.getDishId()));
                }
            }
        }
        return orderedToday;
    }
}
