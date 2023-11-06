package com.engeto.restaurant;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderBook extends ArrayList<Order> {
    private String separator = "\t";

    public void sort(){
        this.sort(new OrderOrderedTimeComparator());
    }

    public void saveToFile(String fileName) throws RestaurantException {

        try (PrintWriter outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            for (Order order : this) {
                outputWriter.println(order.stringToFile(separator));
            }
        } catch (IOException exc) {
            throw new RestaurantException("Error writing OrderBook to file " + exc.getLocalizedMessage());
        }

    }

    public void readFromFile(String fileName) throws RestaurantException {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    parseLineFromFile(line);
                } catch (RestaurantException exception) {
                    throw new RestaurantException("Error reading OrderBook from file: Could not parse line: "
                            + line + " " + exception.getLocalizedMessage());
                }
            }
        } catch (FileNotFoundException exc) {
            throw new RestaurantException("Error reading OrderBook from file: File not found" + exc.getLocalizedMessage());
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
                throw new RestaurantException("Error parsing Order time information from file on line: "+line);
            }
        } else {
            throw new RestaurantException("Error reading Orders from file: incorrect file format on line: " + line);
        }
    }

}
