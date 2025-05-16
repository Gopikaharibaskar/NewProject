package com.mph.main;

import com.mph.loader.MongoTextFileLoader;
import java.io.IOException;

public class BankDataMain {
    public static void main(String[] args) {
        try {
            System.out.println("Processing files from Bank/Control subfolders...");
            MongoTextFileLoader.loadTextFilesToMongoDB();
        } catch (IOException e) {
            System.err.println("Error loading files: " + e.getMessage());
        }
    }
}
