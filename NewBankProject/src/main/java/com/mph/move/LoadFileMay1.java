////file moved to another folder
//package com.mph.loader;
//
//import java.io.*;
//import java.nio.file.*;
//import java.util.regex.*;
//
//public class LoadFileMay1 {
//public static void main(String[] args) {
//    String mainFolder = "Bank";  // Source folder
//    String controlBaseFolder = mainFolder + "/Control"; // Root destination folder
//
//    File rootDir = new File(mainFolder);
//    if (!rootDir.exists() || !rootDir.isDirectory()) {
//        System.out.println("Error: Main folder does not exist!");
//        return;
//    }
//
//    // Loop through all subfolders
//    for (File subFolder : rootDir.listFiles()) {
//        if (subFolder.isDirectory()) {
//            System.out.println("Scanning folder: " + subFolder.getName());
//
//            for (File file : subFolder.listFiles()) {
//                if (file.isFile() && file.getName().endsWith(".txt")) {
//                    String extractedDate = extractDateFromHeader(file);
//
//                    if (extractedDate != null) { // Ensure date is extracted correctly
//                        String destinationFolder = controlBaseFolder + "/" + extractedDate;
//                        File destDir = new File(destinationFolder);
//                        if (!destDir.exists()) {
//                            destDir.mkdirs(); // Create folder based on extracted date
//                        }
//
//                        Path sourcePath = file.toPath();
//                        Path destinationPath = Paths.get(destinationFolder, file.getName());
//
//                        try {
//                            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
//                            System.out.println("Moved " + file.getName() + " to " + destinationPath);
//                        } catch (IOException e) {
//                            System.out.println("Error moving file: " + file.getName());
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//private static String extractDateFromHeader(File file) {
//    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//        String header = reader.readLine().trim();
//        Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}"); // Match YYYY-MM-DD format
//        Matcher matcher = datePattern.matcher(header);
//        if (matcher.find()) {
//            return matcher.group(); // Return extracted date
//        }
//    } catch (IOException e) {
//        System.out.println("Error reading file header: " + file.getName());
//        e.printStackTrace();
//    }
//    return null;
//}
//}


////copy and move file to another folder
//package com.mph.loader;
//
//import java.io.*;
//import java.nio.file.*;
//import java.util.regex.*;
//
//public class LoadFileMay1 {
//public static void main(String[] args) {
//    String mainFolder = "Bank";  // Source folder
//    String controlBaseFolder = mainFolder + "/Control"; // Destination root folder
//
//    File rootDir = new File(mainFolder);
//    if (!rootDir.exists() || !rootDir.isDirectory()) {
//        System.out.println("Error: Main folder does not exist!");
//        return;
//    }
//
//    // Loop through all subfolders
//    for (File subFolder : rootDir.listFiles()) {
//        if (subFolder.isDirectory()) {
//            System.out.println("Scanning folder: " + subFolder.getName());
//
//            for (File file : subFolder.listFiles()) {
//                if (file.isFile() && file.getName().endsWith(".txt")) {
//                    String extractedDate = extractDateFromHeader(file);
//
//                    if (extractedDate != null) { // Ensure date is valid
//                        String destinationFolder = controlBaseFolder + "/" + extractedDate;
//                        File destDir = new File(destinationFolder);
//                        if (!destDir.exists()) {
//                            destDir.mkdirs(); // Create folder for the extracted date
//                        }
//
//                        Path sourcePath = file.toPath();
//                        Path copyDestinationPath = Paths.get(destinationFolder, file.getName());
//
//                        try {
//                            // Copy the file without removing original
//                            Files.copy(sourcePath, copyDestinationPath, StandardCopyOption.REPLACE_EXISTING);
//                            System.out.println("Copied " + file.getName() + " to " + copyDestinationPath);
//                        } catch (IOException e) {
//                            System.out.println("Error copying file: " + file.getName());
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//private static String extractDateFromHeader(File file) {
//    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//        String header = reader.readLine().trim();
//        Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}"); // Match YYYY-MM-DD format
//        Matcher matcher = datePattern.matcher(header);
//        if (matcher.find()) {
//            return matcher.group(); // Return extracted date
//        }
//    } catch (IOException e) {
//        System.out.println("Error reading file header: " + file.getName());
//        e.printStackTrace();
//    }
//    return null;
//}
//}

//copy and move to folder
package com.mph.move;

import java.io.*;
import java.nio.file.*;
import java.util.regex.*;

public class LoadFileMay1 {
public static void main(String[] args) {
    String mainFolder = "Bank";  // Source folder

    File rootDir = new File(mainFolder);
    if (!rootDir.exists() || !rootDir.isDirectory()) {
        System.out.println("Error: Main folder does not exist!");
        return;
    }

    // Loop through all subfolders
    for (File subFolder : rootDir.listFiles()) {
        if (subFolder.isDirectory()) {
            System.out.println("Scanning folder: " + subFolder.getName());

            for (File file : subFolder.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    String extractedDate = extractDateFromHeader(file);

                    if (extractedDate != null) { // Ensure date extraction was successful
                        String destinationFolder = mainFolder + "/" + "Control" + extractedDate; 
                        File destDir = new File(destinationFolder);
                        if (!destDir.exists()) {
                            destDir.mkdirs(); // Automatically create folder inside "Bank"
                        }

                        Path sourcePath = file.toPath();
                        Path copyDestinationPath = Paths.get(destinationFolder, file.getName());

                        try {
                            // Copy the file into its corresponding date-based subfolder
                            Files.copy(sourcePath, copyDestinationPath, StandardCopyOption.REPLACE_EXISTING);
                            System.out.println("Copied " + file.getName() + " to " + copyDestinationPath);
                        } catch (IOException e) {
                            System.out.println("Error copying file: " + file.getName());
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}

private static String extractDateFromHeader(File file) {
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String header = reader.readLine().trim();
        Pattern datePattern = Pattern.compile("\\d{2}-\\d{2}-\\d{4}"); // Match YYYY-MM-DD format
        Matcher matcher = datePattern.matcher(header);
        if (matcher.find()) {
            return matcher.group(); // Return extracted date
        }
    } catch (IOException e) {
        System.out.println("Error reading file header: " + file.getName());
        e.printStackTrace();
    }
    return null;
}
}
