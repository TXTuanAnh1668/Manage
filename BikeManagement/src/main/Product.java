package main;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class Product {
    private final String id;
    private String name;
    private String brandId;
    private String categoryId;
    private int modelYear;
    private double price;
    private static int startId=getCurrentID();
    
    public Product(String id, String name, String brandId, String categoryId, int modelYear, double price) {
        this.id = id;
        this.name = name;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.modelYear = modelYear;
        this.price = price;
    }
    
    public Product(String name, String brandId, String categoryId, int modelYear, double price) {
//        this.id = Tools.int2String(++startId);
        this.id = String.format("A%03d", ++startId);
        this.name = name;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.modelYear = modelYear;
        this.price = price;
    }
    
    private static int getCurrentID(){
        ArrayList<String> ids = Tools.readFile(".\\src\\main\\resources\\products.txt", 0);
        int max=0, numberPart=0;
        String number;
        for (String id: ids){
            number = id.substring(1);
            numberPart = Tools.toInt(number);
            if (numberPart > max){
                max = numberPart;
            }
        }
        return max;
    }
    
    public ArrayList<String> getIDs(String fileName, int location){
        ArrayList<String> ids = Tools.readFile(".\\src\\main\\resources\\"+fileName+".txt", location);
        return ids;
    }
    
    public String getAttrName(String attrName){
        ArrayList<String> ids = this.getIDs(attrName, 0);
        ArrayList<String> names = this.getIDs(attrName, 1);
        String result="";
        String currentID="";
        if (attrName.equalsIgnoreCase("brands"))
            currentID = this.brandId;
        else if (attrName.equalsIgnoreCase("categories"))
            currentID = this.categoryId;
        
        for (int i=0; i<ids.size(); i++){
            if (currentID.equalsIgnoreCase(ids.get(i))){
                result = names.get(i);
            }
        }
        return result;
    }
    
    public String getBrandName(){
        return getAttrName("brands");
    }
    
    public String getCategoryName(){
        return getAttrName("categories");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getId(){
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getBrandId() {
        return brandId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public int getModelYear() {
        return modelYear;
    }

    public double getPrice() {
        return price;
    }
    
    public String id2Name(ArrayList<String> brandNames, ArrayList<String> catNames, ArrayList<String> brandIDs, ArrayList<String> catIds){
        String brandName = "";
        String catName = "";
        for (int i=0; i<brandNames.size(); i++){
            if (this.brandId.equalsIgnoreCase(brandIDs.get(i))){
                brandName = brandNames.get(i);
            }
        }
        for (int j=0; j<catNames.size(); j++){
            if (this.categoryId.equalsIgnoreCase(catIds.get(j))){
                catName = catNames.get(j);
            }
        }
        return String.format("%-5s %-15s %-15s %-15s %-15d %-10.2f", 
                             id, name, brandName, catName, modelYear, price);
    }
    
    @Override
    public String toString() {
        return String.format("%-5s %-15s %-15s %-15s %-15d %-10.2f", 
                             id, name, brandId, categoryId, modelYear, price);
    }
    
    public String saveFormatString(){
        return String.format("%s, %s, %s, %s, %d, %.2f",
                             id, name, brandId, categoryId, modelYear, price);
    }
}
