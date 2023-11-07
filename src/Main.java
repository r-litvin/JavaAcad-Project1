import com.engeto.restaurant.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("Restaurant backend starting up.");

        //test scenario 1 - tasks tested here are task01 - task05
        testScenario();
        //task06: load back from files
        System.out.println("** Task06 **");
        RestaurantManager testManager2 = new RestaurantManager();
        try {
            testManager2.readDishBookFromFile("testScenario-dishBook.txt");
            testManager2.readOrderBookFromFile("testScenario-orderBook.txt");
        } catch (RestaurantException exc){
            System.err.println("Reading files for testManager2 failed: "+exc.getLocalizedMessage());
        }
        testManager2.printOrderListForTable(15);

        System.out.println("Restaurant backend shut down.");
    }

    private static void testAddDishOrderToDishBookFromFile(DishBook dishBook2, OrderBook orderBook2) {
        try {
            Dish newDish = new Dish("Uho", BigDecimal.valueOf(15), 7);
            dishBook2.add(newDish);
            Order newOrder = new Order(7, newDish.getDishId(), 2);
            newOrder.setOrderedTime(LocalDateTime.of(2023,11,4,15,32));
            orderBook2.add(newOrder);
            System.out.println("newDish "+newDish.getDishId());
            System.out.println("NewOrder "+ newOrder);
            System.out.println("DishBook now :");
            //unique numbers don't work
            dishBook2.forEach(System.out::println);

        } catch (Exception exc){}
    }


    private static void testAddDishesOrders(RestaurantManager restaurantManager){
        try {
            Dish dish1 = new Dish("Kuřecí řízek obalovaný 150 g",
                    BigDecimal.valueOf(189.0),
                    35,
                    "Rizek-Kure-01");
            Dish dish2 = new Dish("Hranolky 150 g",
                    BigDecimal.valueOf(99.0),
                    10);
            Dish dish3 = new Dish("Pstruh na víně 200 g",
                    BigDecimal.valueOf(269.0),
                    28,
                    "Pstruh-01");
            Dish dish4 = new Dish("Kofola 0,5 l",
                    BigDecimal.valueOf(55.0),
                    6);
            restaurantManager.addDish(dish1);
            restaurantManager.addDish(dish2);
            restaurantManager.addDish(dish3);
            restaurantManager.addDish(dish4);
            testAddOrders(restaurantManager); //add test Orders as instructed
        } catch (RestaurantException exc){
            System.err.println("Error setting up test dishes and orders: "+exc.getLocalizedMessage());
            }
    }

    private static void testAddOrders(RestaurantManager restaurantManager) throws RestaurantException{
        //pro stul 15
        Order order1 = new Order(15, 0, 2);
        restaurantManager.addOrder(order1);
        Order order2 = new Order(15, 1, 2);
        restaurantManager.addOrder(order2);
        Order order3 = new Order(15, 3, 2);
        order3.setOrderedTime(LocalDateTime.of(2023, 11, 5, 12, 44));
        restaurantManager.addOrder(order3);
        order3.setFulfilmentTime(LocalDateTime.now());
        //pro stul #2
        Order order4 = new Order(2, 0, 1);
        Order order5 = new Order(2, 3, 2);
        Order order6 = new Order(2, 2, 1);
        order4.setOrderedTime(LocalDateTime.of(2023, 11, 5, 14, 34));
        order4.setFulfilmentTime(LocalDateTime.now());
        order5.setOrderedTime(LocalDateTime.of(2023, 11, 5, 14, 34));
        order5.setFulfilmentTime(LocalDateTime.now());
        order5.setPaid(true);
        restaurantManager.addOrder(order4);
        restaurantManager.addOrder(order5);
        restaurantManager.addOrder(order6);
    }

    private static void testSaveDishBook(DishBook dishBook) {
        //test saving DishBook:
        try {
            dishBook.saveToFile("dish_book_00.txt");
        } catch (RestaurantException exc){
            System.err.println("Dish Book could not be saved: "+exc.getLocalizedMessage());
        }
    }

    private static void testScenario(){
        RestaurantManager testRestaurantManager = new RestaurantManager();
        //task01: read data from files
        System.out.println("** Task01 **");
        String dishFileName = "dish_book_10.txt";
        String orderFileName = "order_book_10.txt";
        try {
            testRestaurantManager.readDishBookFromFile(dishFileName);
        } catch (RestaurantException exc){
            System.err.println("Error reading DishBook from file "
                    +dishFileName+" : "+exc.getLocalizedMessage());
        }
        try {
            testRestaurantManager.readOrderBookFromFile(orderFileName);
        } catch (RestaurantException exc){
            System.err.println("Error reading OrderBook from file "
                    +orderFileName+" : "+exc.getLocalizedMessage());
        }
        if(testRestaurantManager.checkOrderBookDishIdConsistency()!=0){
            System.err.println("RestaurantManager has error in order database!");
        }
        //task02: insert test dishes into the system /i.e. dishBook is empty
        System.out.println("** Task02 **");
        if (testRestaurantManager.getDishBookSize()==0){
            testAddDishesOrders(testRestaurantManager);
        }
        //task03: print total cost of orders for table 15
        System.out.println("** Task03 **");
        testRestaurantManager.getTableOrdersPrice(15);
        //task04: demonstrate all management methods:
        //task04-01: print Number of unfulfilled orders
        System.out.println("** Task04-01 **");
        System.out.println("Number of unfulfilled orders is: "
                + testRestaurantManager.countUnfulfilledOrders());
        //task04-02: option to sort OrderBook by orderedTime
        System.out.println("** Task04-02 **");
        testRestaurantManager.sortOrderBook();
        //task04-03: average fulfillment time
        System.out.println("** Task04-03 **");
        System.out.println("Average fulfillment time is: "
                + String.format("%.0f",testRestaurantManager.averageFulfillmentTime())+ " minutes.");
        //task04-04 is missing in the exercise definitions ;-)
        //task04-05: list of dishes ordered today
        System.out.println("** Task04-05 **");
        System.out.println("Dishes ordered today:");
        testRestaurantManager.dishesOrderedToday().forEach(System.out::println);
        //task04-06: Export order list for table in strict format
        System.out.println("** Task04-06 **");
        testRestaurantManager.printOrderListForTable(2);
        //task05: save data to files
        System.out.println("** Task05 **");
        try {
            testRestaurantManager.saveDishBookToFile("testScenario-dishBook.txt");
        } catch (RestaurantException exc){
            System.err.println("Could not save testScenario DishBook! "+exc.getLocalizedMessage());
        }
        try {
            testRestaurantManager.saveOrderBookToFile("testScenario-orderBook.txt");
        } catch (RestaurantException exc){
            System.err.println("Could not save testScenario OrderBook! "+exc.getLocalizedMessage());
        }


    }
}