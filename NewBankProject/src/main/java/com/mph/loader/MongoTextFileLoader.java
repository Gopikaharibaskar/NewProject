package com.mph.loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mph.records.BankTransaction;
import com.mph.records.BankingCustomer;
import com.mph.records.FraudTransaction;

public class MongoTextFileLoader {

    private static final String BANK_DIRECTORY = "C:\\Users\\gopika.h\\eclipse-workspace\\NewBankProject\\Bank";
    private static final String DATABASE_NAME = "BankDB";

    public static void loadTextFilesToMongoDB() throws IOException {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            for (String filePath : fetchTextFiles()) {
                processFile(filePath, database);
            }
        }
    }

    private static List<String> fetchTextFiles() throws IOException {
        return Files.walk(Paths.get(BANK_DIRECTORY), 2) // Scan only Control subfolders
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(filePath -> filePath.contains("Control")) // Detect Control subfolders
                    .toList();
    }

    private static void processFile(String filePath, MongoDatabase database) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            if (lines.size() <= 2) {
                System.out.println("Skipping file: " + filePath + " (Insufficient data)");
                return;
            }

            System.out.println("Processing file: " + filePath);

            for (int i = 1; i < lines.size() - 1; i++) { // Skips header and footer
                String line = lines.get(i);

                String collectionName = determineCollectionType(line);
                if ("UnknownRecords".equals(collectionName)) {
                    System.err.println("Skipping unrecognized record: " + line);
                    continue;
                }

                Class<?> recordType = selectRecordType(collectionName);
                if (recordType == null) {
                    System.err.println("Skipping due to missing record type: " + line);
                    continue;
                }

                MongoCollection<Document> collection = database.getCollection(collectionName);
                Object record = DynamicParser.parseRecord(line, recordType);

                if (record == null) {
                    System.err.println("Skipping record due to parsing error: " + line);
                    continue;
                }

                collection.insertOne(mapRecordToMongoDocument(record));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String determineCollectionType(String line) {
        if (line.startsWith("HDR")) {
            return "Header"; // Skip headers
        } else if (line.startsWith("FTR")) {
            return "Footer"; // Skip footers
        } else if (line.matches("^\\d{5}.*")) { // Customer record starts with a 5-digit ID
            return "CustomerRecords";
        } else if (line.contains("FRAUD")) {
            return "FraudRecords";
        } else if (line.contains("TRANSACTION")) {
            return "TransactionRecords";
        } else {
            System.err.println("Error: Unknown record format → " + line);
            return "UnknownRecords";
        }
    }

    private static Class<?> selectRecordType(String collectionName) {
        return switch (collectionName) {
            case "CustomerRecords" -> BankingCustomer.class;
            case "FraudRecords" -> FraudTransaction.class;
            case "TransactionRecords" -> BankTransaction.class;
            default -> {
                System.err.println("Error: Unknown record type for collection " + collectionName);
                yield null;
            }
        };
    }

    private static Document mapRecordToMongoDocument(Object record) {
        if (record == null) {
            System.err.println("Error: Cannot map a null record.");
            return new Document("error", "Invalid record");
        }

        Document document = new Document();
        for (var field : record.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(com.mph.annotations.FieldLength.class)) {
                try {
                    field.setAccessible(true);
                    document.append(field.getName(), field.get(record).toString().trim());
                } catch (IllegalAccessException e) {
                    System.err.println("Error mapping field: " + field.getName());
                    e.printStackTrace();
                }
            }
        }
        return document;
    }
}
