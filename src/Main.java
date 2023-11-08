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
            restaurantManager.addDish(dish1); restaurantManager.addDish(dish2);
            restaurantManager.addDish(dish3); restaurantManager.addDish(dish4);
            testAddOrders(restaurantManager); //add test Orders as instructed
        } catch (RestaurantException exc){
            System.err.println("Error setting up test dishes and orders: "+exc.getLocalizedMessage());
            }
    }

    private static void testAddOrders(RestaurantManager restaurantManager) throws RestaurantException{
        //pro stul 15
        restaurantManager.addOrder(new Order(15, 0, 2));
        restaurantManager.addOrder(new Order(15, 1, 2));
        Order order3 = new Order(15, 3, 2);
        order3.setOrderedTime(LocalDateTime.of(2023, 11, 5, 12, 44));
        restaurantManager.addOrder(order3);
        order3.setFulfilmentTime(LocalDateTime.now());
        //pro stul #2
        Order order4 = new Order(2, 0, 1);
        Order order5 = new Order(2, 3, 2);
        order4.setOrderedTime(LocalDateTime.of(2023, 11, 5, 14, 34));
        order4.setFulfilmentTime(LocalDateTime.now());
        order5.setOrderedTime(LocalDateTime.of(2023, 11, 5, 14, 34));
        order5.setFulfilmentTime(LocalDateTime.now());
        order5.setPaid(true);
        restaurantManager.addOrder(order4);
        restaurantManager.addOrder(order5);
        restaurantManager.addOrder(new Order(2, 2, 1));
    }

    private static void testScenario(){ //keeping this method longer, just comments+method calls would be 18 lines anyway
        RestaurantManager testRestaurantManager = new RestaurantManager();
        //task01: read data from files
        testTask01readDataFromFiles(testRestaurantManager);
        //task02: insert test dishes into the system /i.e. dishBook is empty
        testTask02addDishesOrders(testRestaurantManager);
        //task03: print total cost of orders for table 15
        testTask03getTableOrdersPrice(testRestaurantManager);
        //task04: demonstrate all management methods:
        //task04-01: print Number of unfulfilled orders
        testTask04_01countUnfulfilledOrders(testRestaurantManager);
        //task04-02: option to sort OrderBook by orderedTime
        testTask04_02sortOrderBook(testRestaurantManager);
        //task04-03: average fulfillment time
        testTask04_03averageFulfillmentTime(testRestaurantManager);
        //task04-04 is missing in the exercise definitions ;-)
        //task04-05: list of dishes ordered today
        testTask04_05dishesOrderedToday(testRestaurantManager);
        //task04-06: Export order list for table in strict format
        testTask04_06printOrderListForTable(testRestaurantManager);
        //task05: save data to files
        testTask05saveDataToFiles(testRestaurantManager);
    }

    private static void testTask03getTableOrdersPrice(RestaurantManager testRestaurantManager) {
        System.out.println("** Task03 **");
        testRestaurantManager.getTableOrdersPrice(15);
    }


    private static void testTask01readDataFromFiles(RestaurantManager testRestaurantManager) {
        System.out.println("** Task01 **");
        String dishFileName = "dish_book_10.txt";
        String orderFileName = "order_book_10.txt";
        try {
            testRestaurantManager.readDishBookFromFile(dishFileName);
        } catch (RestaurantException exc){
            System.err.println("Error in Task01: "+exc.getLocalizedMessage());
        }
        try {
            testRestaurantManager.readOrderBookFromFile(orderFileName);
        } catch (RestaurantException exc){
            System.err.println("Error in Task01: "+exc.getLocalizedMessage());
        }
        if(testRestaurantManager.checkOrderBookDishIdConsistency()!=0){
            System.err.println("RestaurantManager has error in order database!");
        }
    }

    private static void testTask02addDishesOrders(RestaurantManager testRestaurantManager) {
        System.out.println("** Task02 **");
        if (testRestaurantManager.getDishBookSize()==0){
            testAddDishesOrders(testRestaurantManager);
        }
    }

    private static void testTask04_01countUnfulfilledOrders(RestaurantManager testRestaurantManager) {
        System.out.println("** Task04-01 **");
        System.out.println("Number of unfulfilled orders is: "
                + testRestaurantManager.countUnfulfilledOrders());
    }

    private static void testTask04_02sortOrderBook(RestaurantManager testRestaurantManager) {
        System.out.println("** Task04-02 **");
        testRestaurantManager.sortOrderBook();
    }

    private static void testTask04_03averageFulfillmentTime(RestaurantManager testRestaurantManager) {
        System.out.println("** Task04-03 **");
        System.out.println("Average fulfillment time is: "
                + String.format("%.0f", testRestaurantManager.averageFulfillmentTime())+ " minutes.");
    }

    private static void testTask04_05dishesOrderedToday(RestaurantManager testRestaurantManager) {
        System.out.println("** Task04-05 **");
        System.out.println("Dishes ordered today:");
        testRestaurantManager.dishesOrderedToday().forEach(System.out::println);
    }

    private static void testTask04_06printOrderListForTable(RestaurantManager testRestaurantManager) {
        System.out.println("** Task04-06 **");
        testRestaurantManager.printOrderListForTable(2);
    }

    private static void testTask05saveDataToFiles(RestaurantManager testRestaurantManager) {
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