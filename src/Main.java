import com.engeto.restaurant.Dish;
import com.engeto.restaurant.DishBook;
import com.engeto.restaurant.RestaurantException;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

        DishBook dishBook = new DishBook();
        testAddDishes(dishBook);

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
}