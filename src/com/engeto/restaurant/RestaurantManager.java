package com.engeto.restaurant;

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
}
