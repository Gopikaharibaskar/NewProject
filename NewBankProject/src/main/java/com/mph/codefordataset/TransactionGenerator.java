package com.mph.codefordataset;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TransactionGenerator {

    // Fixed values for controlled fields
    private static final String[] TRANSACTION_TYPES = {"Credit", "Debit"};
    private static final String[] STATUSES = {"Success", "Failed", "Pending"};
    private static final String[] CHANNELS = {"ATM", "Online", "Branch", "Mobile"};
    private static final String[] CURRENCIES = {"USD", "EUR", "INR", "GBP", "JPY"};
    private static final String[] CATEGORIES = {"Groceries", "Electronics", "Restaurants", "Fuel", "Utilities", "Travel"};
    private static final String[] MODES = {"NEFT", "RTGS", "IMPS", "UPI", "SWIFT"};

    // Merchant name lists based on categories
    private static final Map<String, String[]> MERCHANT_NAMES = new HashMap<>();
    
    static {
        MERCHANT_NAMES.put("Groceries", new String[]{"Walmart", "Tesco", "Whole Foods", "Target", "Costco", "Kroger"});
        MERCHANT_NAMES.put("Electronics", new String[]{"Best Buy", "Apple Store", "Samsung Electronics", "Bose", "Sony", "Fry's Electronics"});
        MERCHANT_NAMES.put("Restaurants", new String[]{"McDonald's", "Starbucks", "Pizza Hut", "Burger King", "Domino's Pizza", "Chipotle"});
        MERCHANT_NAMES.put("Fuel", new String[]{"Shell", "ExxonMobil", "BP", "Chevron", "Mobil", "Total"});
        MERCHANT_NAMES.put("Utilities", new String[]{"AT&T", "Verizon", "Comcast", "Vodafone", "BT Group", "T-Mobile"});
        MERCHANT_NAMES.put("Travel", new String[]{"Expedia", "Airbnb", "Booking.com", "TripAdvisor", "Delta Airlines", "United Airlines"});
    }

    // Field lengths for fixed-width format
    private static final Map<String, Integer> FIELD_LENGTHS = new HashMap<>();
    
    static {
        FIELD_LENGTHS.put("Transaction_ID", 12);
        FIELD_LENGTHS.put("Customer_ID", 10);
        FIELD_LENGTHS.put("Transaction_Date", 10);
        FIELD_LENGTHS.put("Transaction_Time", 8);
        FIELD_LENGTHS.put("Transaction_Type", 10);
        FIELD_LENGTHS.put("Transaction_Amount", 12);
        FIELD_LENGTHS.put("Currency", 3);
        FIELD_LENGTHS.put("Source_Account", 12);
        FIELD_LENGTHS.put("Destination_Account", 12);
        FIELD_LENGTHS.put("Transaction_Status", 10);
        FIELD_LENGTHS.put("Merchant_Name", 20);
        FIELD_LENGTHS.put("Merchant_Category", 15);
        FIELD_LENGTHS.put("Channel", 10);
        FIELD_LENGTHS.put("Branch_Code", 6);
        FIELD_LENGTHS.put("Transaction_Mode", 8);
    }

    private static String generateRandomString(int length, boolean numericOnly) {
        String characters = numericOnly ? "0123456789" : "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    private static String generateRandomTransaction() {
        Random random = new Random();
        String category = CATEGORIES[random.nextInt(CATEGORIES.length)];
        return String.format("%-12s%-10s%-10s%-8s%-10s%-12s%-3s%-12s%-12s%-10s%-20s%-15s%-10s%-6s%-8s",
                generateRandomString(12, false),
                generateRandomString(10, true),
                "01-05-2025",
                String.format("%02d:%02d:%02d", random.nextInt(24), random.nextInt(60), random.nextInt(60)),
                TRANSACTION_TYPES[random.nextInt(TRANSACTION_TYPES.length)],
                String.format("%.2f", random.nextDouble() * 9990 + 10),
                CURRENCIES[random.nextInt(CURRENCIES.length)],
                generateRandomString(12, true),
                generateRandomString(12, true),
                STATUSES[random.nextInt(STATUSES.length)],
                MERCHANT_NAMES.get(category)[random.nextInt(MERCHANT_NAMES.get(category).length)],
                category,
                CHANNELS[random.nextInt(CHANNELS.length)],
                String.format("%04d", 1001 + random.nextInt(8999)),
                MODES[random.nextInt(MODES.length)]
        );
    }

    public static void main(String[] args) {
        String filename = "transaction_details_fixed.txt";

        try (FileWriter writer = new FileWriter(filename)) {
            for (int i = 0; i < 100; i++) {
                writer.write(generateRandomTransaction() + "\n");
            }
            System.out.println("File saved successfully: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
