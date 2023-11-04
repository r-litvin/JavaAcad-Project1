import com.engeto.restaurant.Dish;
import com.engeto.restaurant.DishBook;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

        DishBook dishBook = new DishBook();
        testAddDishes(dishBook);

        System.out.println("Restaurant backend shut down.");
    }

    private static void testAddDishes(DishBook dishBook){
        Dish dish1 = new Dish("Kuřecí řízek obalovaný 150 g",
                BigDecimal.valueOf(189.0),
                35,
                "Rizek-Kure-01");
        dishBook.add(dish1);
    }
}