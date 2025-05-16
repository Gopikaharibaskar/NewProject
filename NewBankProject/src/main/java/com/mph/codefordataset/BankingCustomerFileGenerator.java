package com.mph.codefordataset;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BankingCustomerFileGenerator {

    // Fixed lists for randomized values
    private static final String[] FIRST_NAMES = {
            "James", "Emily", "Michael", "Sophia", "Daniel", "Olivia", "Matthew", "Isabella", "David",
            "Benjamin", "Abigail", "Ethan", "Mia", "Amelia", "Henry", "Harper", "Sebastian", "Evelyn"
    };
    private static final String[] LAST_NAMES = {
            "Brown", "Smith", "Johnson", "Williams", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris",
            "Martinez", "Clark", "Lewis", "Walker", "Hall", "Allen", "Young", "King", "Wright"
    };
    private static final String[] GENDERS = {"Male", "Female", "Others"};
    private static final String[] EMAIL_PROVIDERS = {"gmail.com"};
    private static final String[] ADDRESSES = {
            "123ElmSt", "456OakAve", "789PineBlvd", "321BirchRd", "654MapleLn", "873RiverDr",
            "112HilltopCt", "945WillowLane", "246LakeView", "808MountainRoad"
    };
    private static final String[] NATIONALITIES = {"American", "Canadian", "Indian", "British", "Australian"};
    private static final String[] OCCUPATIONS = {
            "Engineer", "Doctor", "Teacher", "Manager", "Lawyer", "Artist", "Nurse", "Scientist", "Accountant", "Pilot"
    };
    private static final String[] MARITAL_STATUSES = {"Single", "Married", "Divorced", "Widowed"};
    private static final String[] CUSTOMER_CATEGORIES = {"Regular", "Premium", "VIP"};
    private static final String[] INCOME_LEVELS = {"Low", "Medium", "High"};
    private static final String[] BANKING_SERVICES = {"Loans", "CreditCard", "Savings"};

    private static final Set<String> USED_NAMES = new HashSet<>(); // Ensures unique names

    private static String generateRandomString(String[] array) {
        return array[new Random().nextInt(array.length)];
    }

    private static String generateUniqueName() {
        String name;
        do {
            name = generateRandomString(FIRST_NAMES) + generateRandomString(LAST_NAMES);
        } while (USED_NAMES.contains(name));
        USED_NAMES.add(name);
        return name;
    }

    private static String generateCustomerRecord(int customerId) {
        Random random = new Random();

        String name = generateUniqueName();
        String gender = String.format("%-10s", generateRandomString(GENDERS));
        String dob = String.format("%02d%02d%04d",
                random.nextInt(28) + 1, random.nextInt(12) + 1, random.nextInt(41) + 1985);
        String phone = String.format("%-12d", 7000000000L + random.nextInt(1000000000));
        String email = String.format("%-30s", name.toLowerCase() + random.nextInt(999) + "@" + generateRandomString(EMAIL_PROVIDERS));
        String address = String.format("%-40s", generateRandomString(ADDRESSES));
        String nationality = String.format("%-15s", generateRandomString(NATIONALITIES));
        String occupation = String.format("%-20s", generateRandomString(OCCUPATIONS));
        String maritalStatus = String.format("%-10s", generateRandomString(MARITAL_STATUSES));
        String registrationDateTime = String.format("01-05-2025 %02d:%02d:%02d",
                random.nextInt(24), random.nextInt(60), random.nextInt(60));
        String customerCategory = String.format("%-15s", generateRandomString(CUSTOMER_CATEGORIES));
        String incomeLevel = String.format("%-12s", generateRandomString(INCOME_LEVELS));
        String creditScore = String.format("%5d", random.nextInt(801) + 100);
        String preferredBankingService = String.format("%-15s", generateRandomString(BANKING_SERVICES));

        return String.format("%-6d%-20s%s%s%s%s%s%s%s%s%s%s%s%s%s",
                customerId, name, gender, dob, phone, email, address, nationality,
                occupation, maritalStatus, registrationDateTime, customerCategory,
                incomeLevel, creditScore, preferredBankingService);
    }

    public static void generateBankingCustomerFile(String filePath, int recordCount) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (int i = 10000; i < 10000 + recordCount; i++) {
                writer.write(generateCustomerRecord(i) + "\n");
            }
            System.out.println("Customer file generated successfully at " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String filePath = "banking_customer_details.txt";
        generateBankingCustomerFile(filePath, 100);
    }
}
