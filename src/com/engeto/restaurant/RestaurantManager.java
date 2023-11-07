package com.engeto.restaurant;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class RestaurantManager {
    private DishBook dishBook;
    private OrderBook orderBook;

    public RestaurantManager(){
        this.dishBook = new DishBook();
        this.orderBook = new OrderBook();
    }

    public void readDishBookFromFile(String fileName) throws RestaurantException {
        this.dishBook.readFromFile(fileName);
    }

    public void readOrderBookFromFile(String fileName) throws RestaurantException{
        this.orderBook.readFromFile(fileName);
    }

    public int getDishBookSize(){
        return this.dishBook.size();
    }

    public int getOrderBookSize(){
        return this.orderBook.size();
    }
    /*
    Checks that each Order in orderBook has existing dishId in dishBook.
     */
    public int checkOrderBookDishIdConsistency(){
        for (Order order : this.orderBook){
            if (this.dishBook.get(order.getDishId())==null){
                return 1;
            }
        }
        return 0;
    }

    public void addDish(Dish newDish){
        this.dishBook.add(newDish);
    }

    public void addOrder(Order order) throws RestaurantException{
        if(this.dishBook.get(order.getDishId())!=null){
            this.orderBook.add(order);
        } else {
            throw new RestaurantException("Order cannot by added - Dish is not in dish book!");
        }
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

    public void sortOrderBook(){
        this.orderBook.sort();
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
    /*
    Writes total order cost at indicated table.
     */
    public void getTableOrdersPrice(int tableId){
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        BigDecimal intermediateCost;
        for(Order order : this.orderBook){
            if(order.getTableNumber()==tableId){
                intermediateCost = this.dishBook.get(order.getDishId()).getPrice().multiply(BigDecimal.valueOf(order.getDishCount()));
                totalPrice = totalPrice.add(intermediateCost);
            }
        }
        System.out.println("Total for orders on table "+tableId
                + " is "+totalPrice + " CZK.");
    }
}
