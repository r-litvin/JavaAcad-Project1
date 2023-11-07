package com.engeto.restaurant;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public void saveDishBookToFile(String fileName) throws RestaurantException{
        this.dishBook.saveToFile(fileName);
    }

    public void readOrderBookFromFile(String fileName) throws RestaurantException{
        this.orderBook.readFromFile(fileName);
    }

    public void saveOrderBookToFile(String fileName) throws RestaurantException{
        this.orderBook.saveToFile(fileName);
    }

    public int getDishBookSize(){
        return this.dishBook.size();
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

    public void removeDish(Dish oldDish) throws RestaurantException{
        if(this.dishBook.contains(oldDish)){
            this.dishBook.remove(oldDish);
        } else {
            throw new RestaurantException("Dish cannot be removed - dish is not in DishBook!");
        }
    }

    public void addOrder(Order order) throws RestaurantException{
        if(this.dishBook.get(order.getDishId())!=null){
            this.orderBook.add(order);
        } else {
            throw new RestaurantException("Order cannot by added - Dish is not in dish book!");
        }
    }

    public void removeOrder(Order order) throws RestaurantException{
        if(this.orderBook.contains(order)) {
            this.orderBook.remove(order);
        } else {
            throw new RestaurantException("Order cannot be removed - order does not exist!");
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

    public void printOrderListForTable(int tableNumber) {
        System.out.printf("** Objednávky pro stůl č. %2d **%n****%n",tableNumber);
        int index = 1;
        for(Order order : orderBook){
            if(order.getTableNumber()==tableNumber){
                printOrderNiceFormat(order, index);
                index++;
            }
        }
        System.out.println("******\n");
    }

    private void printOrderNiceFormat(Order order, int index) {
        String formatString;
        BigDecimal totalOrderPrice;
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        formatString = "%d. %s ";
        if(order.getDishCount()>1){ formatString += order.getDishCount()+"x "; }
        totalOrderPrice = this.dishBook.get(order.getDishId()).getPrice().multiply(BigDecimal.valueOf(order.getDishCount()));
        formatString += "("+totalOrderPrice+" Kč):\t";
        //time now
        formatString += order.getOrderedTime().format(timeFormat)+"-";
        if(order.getFulfilmentTime()!=null){ formatString += order.getFulfilmentTime().format(timeFormat);}
        formatString += "\t";
        if(order.isPaid()){ formatString += "zaplaceno"; }
        formatString += "\n";
        System.out.printf(formatString, index, this.dishBook.get(order.getDishId()).getTitle());
    }
}
