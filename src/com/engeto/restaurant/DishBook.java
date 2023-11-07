package com.engeto.restaurant;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class DishBook extends ArrayList<Dish> {
    private String separator = "\t";

    public Dish get(int dishId){
        for(Dish dish : this){
            if(dish.getDishId()==dishId){
                return dish;
            }
        }
        return null;
    }

    public void saveToFile(String fileName) throws RestaurantException {

        try (PrintWriter outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            for (Dish dish : this) {
                outputWriter.println(dish.stringToFile(separator));
            }
        } catch (IOException exc) {
            throw new RestaurantException("Error writing DishBook to file " + exc.getLocalizedMessage());
        }

    }

    public void readFromFile(String fileName) throws RestaurantException {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    parseLineFromFile(line);
                } catch (RestaurantException exception) {
                    throw new RestaurantException("Error reading DishBook from file: "+fileName
                            +" Could not parse line: "
                            + line + " " + exception.getLocalizedMessage());
                }
            }
            this.get(0).setDishIdStart(getHighestId()+1);
        } catch (FileNotFoundException exc) {
            throw new RestaurantException("Error reading DishBook from file: File not found " + exc.getLocalizedMessage());
        }
    }

    private void parseLineFromFile(String line) throws RestaurantException {
        String[] lineItems = line.split(separator);
        if (lineItems.length == 5) {
            int dishId = Integer.parseInt(lineItems[0]);
            String title = lineItems[1];
            BigDecimal price = new BigDecimal(lineItems[2]);
            int preparationTime = Integer.parseInt(lineItems[3]);
            String image = lineItems[4];
            Dish readDish = new Dish(dishId, title, price, preparationTime, image);
            this.add(readDish);
        } else {
            throw new RestaurantException("Error reading Dishes from file: incorrect file format on line: " + line);
        }
    }

    private int getHighestId(){
        int highestId = 0;
        for(Dish dish : this){
            if (dish.getDishId()>highestId){
                highestId = dish.getDishId();
            }

        }
        return highestId;
    }
}
