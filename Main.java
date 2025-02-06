// Project 1

// * Check file path *

import java.util.*;
import java.io.*;



// For orders_errors.txt
class InvalidNumberException extends Exception{
    public InvalidNumberException(String message){
        super(message);
    }
}

class InvalidNameException extends Exception{
    public InvalidNameException(String message){
        super(message);
    }
}

// From products.txt
class Product{
    // Original from file
    private String product_code;
    private String product_name;
    private int product_unitprice;

    // Extra requirement
    private int product_totalCash;
    private int product_totalUnit;

    // Area for method
    public Product(String product_code, String product_name, int product_unitprice){
        this.product_code = product_code;
        this.product_name = product_name;
        this.product_unitprice = product_unitprice;
        this.product_totalCash = 0;
        this.product_totalUnit = 0;
    }

    public void ProductInfo(){
        System.out.printf("%s (%s) unit price = %d",product_name, product_code, product_unitprice);
    }
}



// From orders.txt + product_name
class Order{
    // Original from file
    private int order_id;
    private String order_name;
    private String order_code;
    private int order_unit;
    private int order_plan;

    // Extra requirement
    private int product_name;

    // Area for method
    public Order(int order_id, String order_name, String order_code, int order_unit, int order_plan){
        this.order_id = order_id;
        this.order_name = order_name;
        this.order_code = order_code;
        this.order_unit = order_unit;
        this.order_plan = order_plan;
    }

    public void OrderInfo(){
        System.out.printf("Order %d >> %s  %s x %d  %d-month installments",order_id, order_name, product_name, order_unit, order_plan);
    }
}

// From orders.txt + order_name
class Customer{
    // Original from file
    private String order_name;

    // Extra requirement
    private int order_point;

    // Area for method
}



// From installments.txt
class Installment{
    // Original from file
    private int month;
    private double interest;

    // Extra requirement
    // Area for method
    public Installment(int month, double interest){
        this.month = month;
        this.interest = interest;
    }

    public void InstallInfo(){
        System.out.printf("%d-month plan   monthly interest = %.2f %",month, interest);
    }
}



public class Main{
    // According to the requirement, errors locate in 'orders_errors.txt' only
    public static void CheckingNumber(int[] number) throws InvalidNumberException{
        // int[] number = {order_id, order_unit, order_plan};
    }

    public static void CheckingName(String[] name) throws InvalidNameException{
        // String[] name = {order_name, order_code};
    }

    // For main, didn't add try catch yet
    public static void main(String[] args){

        ArrayList<Product> productsList = new ArrayList<Product>();
        ArrayList<Customer> customersList = new ArrayList<Customer>();
        ArrayList<Installment> installmentsList = new ArrayList<Installment>();

        // Scanner
        Scanner InputScan = new Scanner(System.in);

        // products.txt
        File ProductFile = new File("products.txt");
        Scanner ProductScan = new Scanner(ProductFile);
        System.out.println("Read from " + ProductFile.getPath());
        ProductScan.nextLine();
        while(ProductScan.hasNext()){
            String line = ProductScan.nextLine();
            String [] cols = line.split(",");
            String code = cols[0].trim();
            String productName = cols[1].trim();
            int productPrice = Integer.parseInt(cols[2].trim());

            productsList.add(new Product(code,productName,productPrice));
            System.out.printf("%18s (%2s)    unit price = %,6d",productName,code,productPrice);

        }

        // installments.txt
        File InstallFile = new File("installments.txt");
        //Scanner InstallScan = new Scanner(InstallFile);
        System.out.println("\nRead from" + InstallFile.getPath());

        // orders.txt
        File OrderFile = new File("orders.txt");
        //Scanner OrderScan = new Scanner(OrderFile);
        System.out.println("\nRead from" + OrderFile.getPath());

        // File OrderErrFile = new File("orders_errors.txt")
    }
}