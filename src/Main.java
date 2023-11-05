import com.engeto.restaurant.Dish;
import com.engeto.restaurant.DishBook;
import com.engeto.restaurant.OrderBook;
import com.engeto.restaurant.Order;
import com.engeto.restaurant.RestaurantException;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        System.out.println("Restaurant backend starting up.");
        DishBook dishBook = new DishBook();
        OrderBook orders = new OrderBook();
        testAddDishes(dishBook);
        testAddOrder(orders, dishBook);

        //test saving DishBook:
        try {
            dishBook.saveToFile("dish_book_00.txt");
        } catch (RestaurantException exc){
            System.err.println("Dish Book could not be saved: "+exc.getLocalizedMessage());
        }
        //test loading DishBook from file:
        DishBook dishBook2 = new DishBook();
        try {
            dishBook2.readFromFile("dish_book_01.txt");
        } catch (RestaurantException exc){
            System.err.println("Dish Book could not be read from file "+exc.getLocalizedMessage());
        }
        System.out.println("dishbook2 now has size: "+dishBook2.size()+"");
        System.out.println("dish #2 in dishbook is "+dishBook2.get(2).stringToFile(" "));


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
}