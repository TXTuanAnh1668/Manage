/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;



public class Tools {
    // Read files
//    public static ArrayList<String> readFile(String filepath, int location){
//        ArrayList<String> myIDList = new ArrayList<>();
//        try (InputStream inputStream = new FileInputStream(filepath);
//            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){
//            String line;
//            while ((line = br.readLine()) != null){
//                String[] parts = line.split(",");
//                if (parts.length > 0){
//                    myIDList.add(parts[location].trim());
//                }
//            }
//        } catch (IOException e){
//            System.err.println("Error reading from file: "+e.getMessage());
//        }
//        return myIDList;
//    }
    public static ArrayList<String> readFile(String filepath, int location){
        ArrayList<String> myIDList = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(filepath);
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){
                String line;
                while((line = br.readLine()) != null){
                    String[] parts = line.split(",");
                    if(parts.length > 0){
                        myIDList.add(parts[location].trim());
                    }
                }
        }catch(IOException e){
            System.err.println("Error reading from file: "+e.getMessage());
        }
        return myIDList;
    }
    // Save files
//    public static void writeFile(ProductList list, String filePath) {
//        try (OutputStream outputStream = new FileOutputStream(filePath,false);
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
//            for (Product item : list) {
//                writer.write(item.saveFormatString());
//                writer.newLine();
//            }
//        } catch (IOException e) {
//            System.err.println("Error writing to file: " + e.getMessage());
//        }
//    }
    public static void writeFile(ProductList list, String filepath){
        try(OutputStream outputStream = new FileOutputStream(filepath, false);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))){
                for(Product item : list){
                    writer.write(item.saveFormatString());
                    writer.newLine();
                }
        }catch(IOException e){
            System.err.println("Error writting to file: "+e.getMessage());
        }
    }
    // Validations
    public static boolean validateID(String value, String type){
        ArrayList<String> IDs = Tools.readFile(".\\src\\main\\resources\\"+type+".txt", 0);
        for (String ID: IDs){
            if (value.equalsIgnoreCase(ID)){
                return true;
            }
        }
        System.out.println(type.toUpperCase()+" ID does not exist!");
        return false;
    }
    
    public static String validateProductID(String value){
        boolean result = Tools.validateID(value, "products");
        while (!result){
            value = Tools.getString("Re-enter Product ID: ");
            result = Tools.validateID(value, "products");
        }
        return value;
    }
    
    public static String validateName(String message){
        return Tools.getString(message);
    }
    
    public static String validateBrandID(String value){
        boolean result = Tools.validateID(value, "brands");
        while (!result){
            value = Tools.getString("Re-enter Brand ID: ");
            result = Tools.validateID(value, "brands");
        }
        return value;
    }
    
    public static String validateCategoryID(String value){
        boolean result = Tools.validateID(value, "categories");
        while (!result){
            value = Tools.getString("Re-enter Category ID: ");
            result = Tools.validateID(value, "categories");
        }
        return value;
    }
   
    // Scanners
    public static String int2String(int input){
        return String.valueOf(input);
    }
    public static String getString(String message){
        Scanner sc = new Scanner(System.in);
        String result;
        do {
            System.out.print(message);
            result = sc.nextLine();
            if (result.isEmpty()){
                System.out.println("Found empty input!");
            }
        } while (result.isEmpty());
        return result;
    }
    
    public static int toInt(String value){
        return Integer.parseInt(value);
    }
    
    public static int getInt(String message){
        while (true){
            try {
                String value = getString(message);
                return Tools.toInt(value);
            } catch (NumberFormatException e){
                System.out.println("Only numbers are supported!");
            }
        }
    }
    
    public static double toDouble(String value){
        return Double.parseDouble(value);
    }
    
    public static double getDouble(String message){
        while (true){
            try {
                String value = getString(message);
                return Tools.toDouble(value);
            } catch (NumberFormatException e){
                System.out.println("Only numbers are supported.");
            }
        }
    }
}
