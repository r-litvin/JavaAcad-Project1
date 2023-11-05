package com.engeto.restaurant;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class OrderBook extends ArrayList<Order> {
    private String separator = "\t";

    public void saveToFile(String fileName) throws RestaurantException {

        try (PrintWriter outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            for (Order order : this) {
                outputWriter.println(order.stringToFile(separator));
            }
        } catch (IOException exc) {
            throw new RestaurantException("Error writing OrderBook to file " + exc.getLocalizedMessage());
        }

    }

    private void parseLineFromFile(String line) throws RestaurantException {
        String[] lineItems = line.split(separator);
        if (lineItems.length == 5) {
            try {
                int tableNumber = Integer.parseInt(lineItems[0]);
                int dishId = Integer.parseInt(lineItems[1]);
                LocalDateTime orderedTime = LocalDateTime.parse(lineItems[2]);
                LocalDateTime fulfilmentTime = LocalDateTime.parse(lineItems[3]);
                boolean isPaid = Boolean.parseBoolean(lineItems[4]);
                Order readOrder = new Order(tableNumber, dishId, orderedTime, fulfilmentTime, isPaid);
                this.add(readOrder);
            } catch (NumberFormatException exc){
                throw new RestaurantException("Error parsing Orders from file on line: "+line);
            } catch (DateTimeParseException exc){
                throw new RestaurantException("Error parsing Orders from file on line: "+line);
            }
        } else {
            throw new RestaurantException("Error reading Dishes from file: incorrect file format on line: " + line);
        }
    }

}
