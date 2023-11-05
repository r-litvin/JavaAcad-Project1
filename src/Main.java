import com.engeto.restaurant.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Restaurant backend starting up.");
        DishBook dishBook = new DishBook();
        OrderBook orders = new OrderBook();
        testAddDishes(dishBook);
        testAddOrder(orders, dishBook);

        testSaveDishBook(dishBook);
        //test loading DishBook from file:
        DishBook dishBook2 = testReadDishBookFromFile();
        //test orders
        //Zákazníci u stolu 15 si objednali
        // dvakrát kuřecí řízek,
        // dvakrát hranolky a
        // dvakrát Kofolu.
        // Kofolu už dostali, na řízek ještě čekají.
        OrderBook orderBook2 = new OrderBook();
        Order order1 = new Order(15, 0);
        Order order2 = new Order(15, 0);
        orderBook2.add(order1); orderBook2.add(order2);
        Order order3 = new Order(15, 1);
        Order order4 = new Order(15, 1);
        orderBook2.add(order3); orderBook2.add(order4);
        Order order5 = new Order(15, 3);
        order5.setOrderedTime(LocalDateTime.of(2023, 11,5,12,44));
        Order order6 = new Order(15, 3);
        orderBook2.add(order5); orderBook2.add(order6);
        order5.setFulfilmentTime(LocalDateTime.now()); order6.setFulfilmentTime(LocalDateTime.now());
        //pro stul #2
        Order order7 = new Order(2, 0); Order order8 = new Order(2, 3);
        Order order9 = new Order(2, 2); Order order10 = new Order(2, 3);
        order7.setOrderedTime(LocalDateTime.of(2023,11,5,14,34));
        order7.setFulfilmentTime(LocalDateTime.now());
        orderBook2.add(order7); orderBook2.add(order8);
        orderBook2.add(order9); orderBook2.add(order10);
        mapOrdersToTablePrice(orderBook2, 15, dishBook2);

        //System.out.println("order7 fulfilment "+order7.getFulfilmentTime()); //null if empty
        try {
            Dish newDish = new Dish("Uho", BigDecimal.valueOf(15), 7);
            dishBook2.add(newDish);
            Order newOrder = new Order(7,newDish.getDishId());
            newOrder.setOrderedTime(LocalDateTime.of(2023,11,4,15,32));
            orderBook2.add(newOrder);
            System.out.println("newDish "+newDish.getDishId());
            System.out.println("NewOrder "+ newOrder);
            System.out.println("DishBook now :");
            //unique numbers don't work
            dishBook2.forEach(System.out::println);

        } catch (Exception exc){}


        //task01; task03;
        testRestaurantManager(dishBook2, orderBook2);
        //task02 - option to sort by orderedTime
        orderBook2.sort(new OrderOrderedTimeComparator());
        //orderBook2.forEach(System.out::println);

        //System.out.println("minutes diff for order7: "+
        //                ChronoUnit.MINUTES.between(order5.getOrderedTime(), order5.getFulfilmentTime()));
        //task03 - average fulfillment time






        System.out.println("Restaurant backend shut down.");
    }




    private static void testAddDishes(DishBook dishBook){
        try {
            Dish dish1 = new Dish("Kuřecí řízek obalovaný 150 g",
                    BigDecimal.valueOf(189.0),
                    35,
                    "Rizek-Kure-01");
            dishBook.add(dish1);
        } catch (RestaurantException exc){
            System.err.println("Error setting up 'rizek': "+exc.getLocalizedMessage());
            }
    }

    private static void testAddOrder(OrderBook orders, DishBook dishes){
        Order order1 = new Order(1, dishes.get(0).getDishId());
        System.out.println("test: order1 for table "+
                order1.getTableNumber()+" has dishId "+
                order1.getDishId());
        orders.add(order1);
    }

    private static void testSaveDishBook(DishBook dishBook) {
        //test saving DishBook:
        try {
            dishBook.saveToFile("dish_book_00.txt");
        } catch (RestaurantException exc){
            System.err.println("Dish Book could not be saved: "+exc.getLocalizedMessage());
        }
    }

    private static DishBook testReadDishBookFromFile() {
        DishBook dishBook2 = new DishBook();
        try {
            dishBook2.readFromFile("dish_book_01.txt");
        } catch (RestaurantException exc){
            System.err.println("Dish Book could not be read from file "+exc.getLocalizedMessage());
        }
        System.out.println("dishbook2 now has size: "+ dishBook2.size()+"");
        System.out.println("dish #2 in dishbook is "+ dishBook2.get(2).stringToFile(" "));
        return dishBook2;
    }

    private static void mapOrdersToTablePrice(OrderBook orderBook, int tableId, DishBook dishBook){
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for(Order order : orderBook){
            if(order.getTableNumber()==tableId){
                totalPrice = totalPrice.add(dishBook.get(order.getDishId()).getPrice());
            }
        }
        System.out.println("Total for orders on table "+tableId
                + " is "+totalPrice + " CZK.");
    }

    public static void testRestaurantManager(DishBook dishBook, OrderBook orderBook){
        RestaurantManager restaurantManager = new RestaurantManager(dishBook, orderBook);
        //task01 - count unfulfilled orders
        System.out.println("Number of unfulfilled orders is: "
                + restaurantManager.countUnfulfilledOrders());
        //task03 - average fulfillment time
        System.out.println("Average fulfillment time is: "
                + String.format("%.0f",restaurantManager.averageFulfillmentTime())+ " minutes.");
        //task05 - list of dishes ordered today
        System.out.println("Dishes ordered today:");
        restaurantManager.dishesOrderedToday().forEach(System.out::println);
    }
}