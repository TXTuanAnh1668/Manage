package main;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class ProductList extends ArrayList<Product>{
    private final ArrayList<String> brandIDs;
    private final ArrayList<String> brandNames;
    private final ArrayList<String> categoryIDs;
    private final ArrayList<String> catNames;

    public ProductList(){
        this.brandIDs = Tools.readFile(".\\src\\main\\resources\\brands.txt", 0);
        this.brandNames = Tools.readFile(".\\src\\main\\resources\\brands.txt", 1);
        this.categoryIDs = Tools.readFile(".\\src\\main\\resources\\categories.txt", 0);
        this.catNames = Tools.readFile(".\\src\\main\\resources\\categories.txt", 1);
    }
    
    public void addProduct(){
        String name = Tools.validateName("Enter product name: ");
        this.displayBrands();
        String brandId = Tools.validateBrandID("Enter brand id: ");
        this.displayCategories();
        String categoryId = Tools.validateCategoryID("Enter category id: ");
        int modelYear = Tools.getInt("Enter model year: ");
        double price = Tools.getDouble("Enter price: ");
        Product product = new Product(name, brandId, categoryId, modelYear, price);
        this.add(product);
    }
    
    public void loadProductList(){
        String filepath = ".\\src\\main\\resources\\products.txt";
        try (InputStream inputStream = new FileInputStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){
            String line;
            while ((line = br.readLine()) != null){
                String[] parts = line.split(",");
                this.add(new Product(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim(), Tools.toInt(parts[4].trim()), Tools.toDouble(parts[5].trim())));
            }
        } catch (IOException e){
            System.err.println("Error reading from file: "+e.getMessage());
        }
    }
    
    public Product findById(String id){
        for (Product product: this){
            if (product.getId().equalsIgnoreCase(id)){
                return product;
            }
        }
        return null;
    }
    
    public void addSampleData(){
        String name="";
        int brandId=0;
        int catId=0;
        int price=0;
        int modelYear=0;
        for (int i=0; i<5; i++){
            name = "Bike "+ i;
            brandId = i;
            catId = i;
            price = i*102;
            modelYear = i*1024;
            this.add(new Product(
                    name, 
                    String.format("B%03d", brandId),
                    String.format("C%03d", catId),
                    modelYear, 
                    price
                )
            );
        }
        this.add(new Product(
                name, 
                String.format("B%03d", brandId),
                String.format("C%03d", catId),
                modelYear, 
                price
            )
        );
        this.display(this);
    }
    
    public void display(ProductList productList) {
        int repeatTimes = 80;
        String line;
        line = new String(new char[repeatTimes]).replace("\0", "-");
        System.out.println(line);
        System.out.printf(
                "%-5s %-15s %-15s %-15s %-15s %-10s\n", 
                "ID", "Name", "Brand Name", "Category Name", "Model Year", "Price"
        );
        System.out.println(line);
        for (Product product: productList) {
            System.out.println(product.id2Name(
                    this.brandNames, this.catNames,
                    this.brandIDs, this.categoryIDs
            ));
        }
        System.out.println(line);
    }

    
    public void display(){
        display(this);
    }
    
    
    
    
    
    public void displayCategories() {
        String header = String.format("%-5s | %s", "ID", "Category Name");
        System.out.println("Available categories:");

        StringBuilder lineBuilder = new StringBuilder();
        for (int i = 0; i < header.length() + 2; i++) {
            lineBuilder.append("-");
        }
        String line = lineBuilder.toString();

        System.out.println(line);
        System.out.println(header);
        System.out.println(line);

        for (int i = 0; i < this.categoryIDs.size(); i++) {
            System.out.printf("%-5s | %s%n", this.categoryIDs.get(i), this.catNames.get(i));
        }
        System.out.println(line);
    }
    
    public void displayBrands() {
        String header = String.format("%-5s | %s", "ID", "Brand Name");
        System.out.println("Available brands:");

        StringBuilder lineBuilder = new StringBuilder();
        for (int i = 0; i < header.length() + 2; i++) {
            lineBuilder.append("-");
        }
        String line = lineBuilder.toString();

        System.out.println(line);
        System.out.println(header);
        System.out.println(line);

        for (int i = 0; i < this.brandIDs.size(); i++) {
            System.out.printf("%-5s | %s%n", this.brandIDs.get(i), this.brandNames.get(i));
        }
        System.out.println(line);
    }
    
    public void sortByID(int reverse){
        Comparator<Product> comparator = Comparator.comparing(Product::getId);
        if (reverse == 1)
            this.sort(comparator.reversed());
        else 
            this.sort(comparator);
        this.display();
    }
    
    public void sortByName(int reverse){
        Comparator<Product> comparator = Comparator.comparing(Product::getName);
        if (reverse == 1)
            this.sort(comparator.reversed());
        else 
            this.sort(comparator);
        this.display();
    }
    
    public void sortByBrand(int reverse){
        Comparator<Product> comparator = Comparator.comparing(Product::getBrandName);
        if (reverse == 1)
            this.sort(comparator.reversed());
        else 
            this.sort(comparator);
        this.display();
    }
    
    public void sortByCategory(int reverse){
        Comparator<Product> comparator = Comparator.comparing(Product::getCategoryName);
        if (reverse == 1)
            this.sort(comparator.reversed());
        else 
            this.sort(comparator);
        this.display();
    }
    
    public void sortByYear(int reverse){
        Comparator<Product> comparator = Comparator.comparingInt(Product::getModelYear);
        if (reverse == 1)
            this.sort(comparator.reversed());
        else 
            this.sort(comparator);
        this.display();
    }
    
    public void sortByPrice(int reverse){
        Comparator<Product> comparator = Comparator.comparingDouble(Product::getPrice);
        if (reverse == 1)
            this.sort(comparator.reversed());
        else 
            this.sort(comparator);
        this.display();
    }
    
    public void sortBy(int choice, int reverse){
        switch (choice){
            case 0:
                this.sortByID(reverse);
                break;
            case 1:
                this.sortByName(reverse);
                break;
            case 2:
                this.sortByBrand(reverse);
                break;
            case 3:
                this.sortByCategory(reverse);
                break;
            case 4:
                this.sortByYear(reverse);
                break;
            case 5:
                this.sortByPrice(reverse);
                break;
        }
    }
    
    
    private void updateProductInfo(Product product, int option, String newValue){
        int choice=0;
        newValue = newValue.toUpperCase();
        switch (option){
            case 1:
                choice = Tools.getInt("You want to change "+product.getName()+" -> "+newValue+"? Confirm [0/1] ");
                if (choice != 0)
                    product.setName(newValue);
                break;
            case 2:
                newValue = Tools.validateBrandID(newValue);
                choice = Tools.getInt("You want to change "+product.getBrandId()+" -> "+newValue+"? Confirm [0/1] ");
                if (choice != 0)
                    product.setBrandId(newValue);
                break;
            case 3:
                newValue = Tools.validateCategoryID(newValue);
                choice = Tools.getInt("You want to change "+product.getCategoryId()+" -> "+newValue+"? Confirm [0/1] ");
                if (choice != 0)
                    product.setCategoryId(newValue);
                break;
            case 4:
                choice = Tools.getInt("You want to change "+product.getModelYear()+" -> "+newValue+"? Confirm [0/1] ");
                if (choice != 0)
                    product.setModelYear(Tools.toInt(newValue));
                break;
            case 5:
                choice = Tools.getInt("You want to change "+product.getPrice()+" -> "+newValue+"? Confirm [0/1] ");
                if (choice != 0)
                    product.setPrice(Tools.toDouble(newValue));
                break;
        }
        if (choice != 0){
            System.out.println("Update successfully.");
        } else {
            System.out.println("Update failed.");
        }
    }
    
    public void updateProduct(String id, int choice, String newValue){
        Product product = findById(id);
        this.updateProductInfo(product, choice, newValue);
        this.display();
    }
    
    public Product deleteById(String id){
        Product product = this.findById(id);
        this.remove(product);
        return product;
    }
    
    public ProductList searchByName(String s){
        ProductList newList = new ProductList();
        s = s.toLowerCase();
        for (Product product: this){
            if (product.getName().toLowerCase().contains(s)){
                newList.add(product);
            }
        }
        if (!newList.isEmpty()){
            newList.sort(Comparator.comparingInt(Product::getModelYear));
        }
        return newList;
    }
    
}
