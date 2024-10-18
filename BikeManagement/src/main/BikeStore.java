package main;


public class BikeStore {
    private static BikeStore instance;
    private ProductList myList;
    private BikeStore(){
        this.myList = new ProductList();
        myList.loadProductList();
    }
    public static BikeStore getBikeStore(){
        if (instance == null)
            instance = new BikeStore();
        return instance;
    }
    private void displayMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Add a product");
        System.out.println("2. Search product by name");
        System.out.println("3. Update product");
        System.out.println("4. Delete product");
        System.out.println("5. Save products to file");
        System.out.println("6. Print list products from file");
        System.out.println("7. Add sample data");
        System.out.println("8. Sort");
        System.out.println("0. Quit\n");
    }
    
    public void addProduct(ProductList productList){
        int choice;
        do {
            productList.addProduct();
            System.out.println("Successfully add new product to list!!\n");
            choice = Tools.getInt("Add one more? [0/1] ");
        } while (choice != 0);
        productList.display();
    }
    
    public void searchProductByName(ProductList productList){
        String name;
        int choice;
        if (!productList.isEmpty()){
            do {
                name = Tools.getString("Enter name to search: ");
                ProductList searchList = productList.searchByName(name);
                if (searchList.isEmpty()){
                    System.out.println("Have no any Product.");
                } else {
                    searchList.display();
                }
                choice = Tools.getInt("One more search? [0/1] ");
            } while (choice != 0);
        } else {
            System.out.println("Found empty list. Cannot operate your selection!");
        }
    }
    
    public void updateProduct(ProductList productList){
        int option, choice;
        String id, newValue;
        boolean isChoiceValid=false;
        if (!productList.isEmpty()){
            do {
                
                id = Tools.getString("Enter product ID to update: ");
                id = Tools.validateProductID(id);
                do {
                    option = Tools.getInt("Enter your choice [name(1)/brand id(2)/category id(3)/model year(4)/price(5)/Exit(-1)]: ");
                    if (option >= 1 && option <=5){
                        isChoiceValid = true;
                    }
                } while (!isChoiceValid);
                switch (option){
                    case 2:
                        productList.displayBrands();
                        break;
                    case 3:
                        productList.displayCategories();
                        break;
                }
                newValue = Tools.getString("New value: ");
                productList.updateProduct(id, option, newValue);
                choice = Tools.getInt("Update one more? [0/1] ");
            } while (choice != 0);
        } else {
            System.out.println("Found empty list. Cannot operate your selection!");
        }
        saveProductsToFile(myList);
    }
    
    public void deleteProduct(ProductList productList){
        int choice;
        String id;
        ProductList deletedOnes = new ProductList();
        if (!productList.isEmpty()){
            do {
                id = Tools.getString("Enter product ID to delete: ");
                Product removedProduct = productList.deleteById(id);
                deletedOnes.add(removedProduct);
                if (removedProduct != null){
                    System.out.println("Remove successfully.");
                } else {
                    System.out.println("Remove failed.");
                }
                productList.display();
                choice = Tools.getInt("Delete one more? [0/1] ");
            } while (choice != 0);
            saveDeletedProductData(deletedOnes);
            saveProductsToFile(myList);
        } else {
            System.out.println("Found empty list. Cannot operate your selection!");
        }
    }
    
    public void saveDeletedProductData(ProductList myList){
        Tools.writeFile(myList, ".\\src\\main\\resources\\deleted_products.txt");
    }
    
    public void saveProductsToFile(ProductList myList) {
        Tools.writeFile(myList, ".\\src\\main\\resources\\products.txt");
    }
    
    public void sort(ProductList myList){
        int option, reverse;
        option = Tools.getInt("Sort by [id(0)/name(1)/brand name(2)/category name(3)/model year(4)/price(5)]/Exit(-1): ");
        do {
            reverse = Tools.getInt("Ascending(0)/Descending(1) ");
            myList.sortBy(option, reverse);
            option = Tools.getInt("Sort by [id(0)/name(1)/brand name(2)/category name(3)/model year(4)/price(5)]/Exit(-1): ");
        } while (option >= 0 && option <= 5);
    }
    
    
    
    public void run(){
        int choice=0;
        do {
            displayMenu();
            choice = Tools.getInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    addProduct(myList);
                    break;
                case 2:
                    searchProductByName(myList);
                    break;
                case 3:
                    updateProduct(myList);
                    break;
                case 4:
                    deleteProduct(myList);
                    break;
                case 5:
                    saveProductsToFile(myList);
                    break;
                case 6:
                    myList.display();
                    break;
                case 7:
                    myList.addSampleData();
                    break;
                case 8:
                    sort(myList);
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice > 0);
    }
}
