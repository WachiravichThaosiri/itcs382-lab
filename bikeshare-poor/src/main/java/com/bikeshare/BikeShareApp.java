package com.bikeshare;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class BikeShareApp {

    // Poor design: global public static state shared across the program
    public static int libraryBikes = 2;
    public static int canteenBikes = 2;
    public static int dormBikes = 2;
    
    public static String currentMemberId = "";
    public static int currentMemberType = 1; // 1 = student, 2 = staff

    public static void main(String[] args) {
        System.out.println("Commands:");
        System.out.println(" rent <id> <type(1=student/2=staff)> <station>");
        System.out.println(" return <station> <minutes>");
        System.out.println(" quit");

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running && scanner.hasNextLine()) {
                System.out.print("> ");
                String input = scanner.nextLine().trim();
                
                if (input.equalsIgnoreCase("quit")) {
                    running = false;
                    continue;
                }

                try {
                    String[] parts = input.split(" ");
                    String command = parts[0].toLowerCase();

                    if (command.equals("rent")) {
                        currentMemberId = parts[1];
                        currentMemberType = Integer.parseInt(parts[2]);
                        String station = parts[3].toLowerCase();
                        processTransaction(true, station, 0);
                    } else if (command.equals("return")) {
                        String station = parts[1].toLowerCase();
                        int minutes = Integer.parseInt(parts[2]);
                        processTransaction(false, station, minutes);
                    } else {
                        System.out.println("Unknown command.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input format. " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Scanner error: " + e.getMessage());
        }
    }

    // Poor design: A boolean flag drastically alters what this function actually does
    public static void processTransaction(boolean isRent, String station, int minutes) {
        int bikesAvailable = getStationBikes(station);

        if (isRent) {
            if (bikesAvailable > 0) {
                setStationBikes(station, bikesAvailable - 1);
                System.out.println("Bike rented from " + station + ".");
            } else {
                System.out.println("No bikes available at " + station + ".");
            }
        } else {
            setStationBikes(station, bikesAvailable + 1);
            
            // 2 baht for student (code 1), 3 baht for staff (code 2)
            int rate = (currentMemberType == 1) ? 2 : 3;
            int fee = minutes * rate;
            
            System.out.println("--- Receipt ---");
            System.out.println("Bike returned at: " + station);
            System.out.println("Minutes ridden: " + minutes);
            System.out.println("Total fee: " + fee + " baht");
            System.out.println("---------------");

            try (FileWriter writer = new FileWriter("rides.csv", true)) {
                writer.write(currentMemberId + "," + currentMemberType + "," + station + "," + minutes + "," + fee + "\n");
            } catch (IOException e) {
                System.out.println("Failed to save record to rides.csv: " + e.getMessage());
            }
        }
    }

    private static int getStationBikes(String station) {
        if (station.equals("library")) return libraryBikes;
        if (station.equals("canteen")) return canteenBikes;
        if (station.equals("dorm")) return dormBikes;
        return 0;
    }

    private static void setStationBikes(String station, int count) {
        if (station.equals("library")) libraryBikes = count;
        else if (station.equals("canteen")) canteenBikes = count;
        else if (station.equals("dorm")) dormBikes = count;
    }
}