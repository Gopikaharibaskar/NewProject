package com.mph.codefordataset;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FraudDetectionFileGenerator {

    // Fixed values for controlled fields
    private static final String[] TRANSACTION_TYPES = {"Credit", "Debit"};
    private static final String[] CURRENCIES = {"USD", "EUR", "INR", "GBP", "JPY"};
    private static final String[] CATEGORIES = {"Groceries", "Electronics", "Restaurants", "Fuel", "Utilities", "Travel"};
    private static final String[] CHANNELS = {"ATM", "Online", "Mobile"};
    private static final String[] FRAUD_STATUSES = {"Fraud", "No Fraud", "Pending"};
    private static final String[] SUSPICIOUS_FLAGS = {"Yes", "No"};

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

    private static String generateRandomString(int length, boolean numericOnly) {
        String characters = numericOnly ? "0123456789" : "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    private static String generateTransactionRecord() {
        Random random = new Random();
        String category = CATEGORIES[random.nextInt(CATEGORIES.length)];
        String merchant = MERCHANT_NAMES.get(category)[random.nextInt(MERCHANT_NAMES.get(category).length)];
        String fraudStatus = FRAUD_STATUSES[random.nextInt(FRAUD_STATUSES.length)];
        String suspiciousFlag = SUSPICIOUS_FLAGS[random.nextInt(SUSPICIOUS_FLAGS.length)];

        return String.format("%-12s%-10s%-10s%-8s%-10s%-12s%-3s%-12s%-12s%-20s%-15s%-10s%-4s%-10s%-3s",
                generateRandomString(12, false),
                generateRandomString(10, true),
                "01-05-2025",  // Fixed date
                String.format("%02d:%02d:%02d", random.nextInt(24), random.nextInt(60), random.nextInt(60)),
                TRANSACTION_TYPES[random.nextInt(TRANSACTION_TYPES.length)],
                String.format("%.2f", random.nextDouble() * 9990 + 10),
                CURRENCIES[random.nextInt(CURRENCIES.length)],
                generateRandomString(12, true),
                generateRandomString(12, true),
                merchant,
                category,
                fraudStatus,
                String.valueOf(random.nextInt(51) + 50),  // Risk Score (50-100)
                CHANNELS[random.nextInt(CHANNELS.length)],
                suspiciousFlag
        );
    }

    public static void main(String[] args) {
        String filename = "fraud_detection_file.txt";

        // Header and Footer
        String fileIdentifier = "00100";
        String date = "01-05-2025";
        String header = "HDR" + date + fileIdentifier + "BANKFRAUDDETECT";
        String footer = "FTR" + date + fileIdentifier + "ENDOFRAUDFILE";

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(header + "\n");

            for (int i = 0; i < 100; i++) {
                writer.write(generateTransactionRecord() + "\n");
            }

            writer.write("\n" + footer);
            System.out.println("File saved successfully: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
