// Project 1

// * Check file path *

import java.io.*;
import java.util.*;


class InvalidProductExcetion extends Exception{
    public InvalidProductExcetion(String message){
        super(message);
    }
}
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
        System.out.printf("%-15s (%s) unit price = %d\n",product_name, product_code, product_unitprice);
    }

    public String getProductCode(){
        return product_code;
    }

    public String getProductName(){
        return product_name;
    }

}



// From orders.txt + product_name
class Order{
    // Original from file
    private int order_id;
    private Customer order_name;
    private Product order_code;
    private int order_unit;
    private int order_plan;

    // Extra requirement
    private int product_name;

    // Area for method
    public Order(int order_id, Customer order_name, Product order_code, int order_unit, int order_plan){
        this.order_id = order_id;
        this.order_name = order_name;
        this.order_code = order_code;
        this.order_unit = order_unit;
        this.order_plan = order_plan;
    }

    public void OrderInfo(){
        System.out.printf("Order %d >> %s  %s x %d  %d-month installments\n",order_id, order_name, product_name, order_unit, order_plan);
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
        System.out.printf("%2d-month plans   monthly interest = %.2f %%\n",month, interest);
    }
}



public class Main{
    public static String checkFile(String  fileName){
        String path = "";
        fileName = path+fileName;
        while(true){
            File newFile = new File(fileName);
            try(Scanner inFile = new Scanner(newFile)){
                System.out.println("\nRead from " + newFile.getPath());
                break;
            }catch(Exception e){
                System.out.println(e);
                System.out.println("New File Name =");
                Scanner input = new Scanner(System.in);
                fileName = input.nextLine();
            }
        }
        return fileName;
    }


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
        try{
            // products.txt
            File ProductFile = new File(checkFile("products.txt"));
            Scanner ProductScan = new Scanner(ProductFile);
            ProductScan.nextLine();
            while(ProductScan.hasNext()){
                String line = ProductScan.nextLine();
                String [] cols = line.split(",");
                String code = cols[0].trim();
                String productName = cols[1].trim();
                int productPrice = Integer.parseInt(cols[2].trim());

                productsList.add(new Product(code,productName,productPrice));
            
            } 
                for(int i=0;i<productsList.size();i++){
                    productsList.get(i).ProductInfo();
                }
            
            System.out.println("");

            // installments.txt
            File InstallFile = new File(checkFile("installments.txt"));
            Scanner InstallScan = new Scanner(InstallFile);
            InstallScan.nextLine();
            while(InstallScan.hasNext()){
                String line = InstallScan.nextLine();
                String [] cols = line.split(",");
                int months = Integer.parseInt(cols[0]);
                double interest = Double.parseDouble(cols[1]);

                installmentsList.add(new Installment(months,interest));

            }
            for(int i=0;i<installmentsList.size();i++){
                    installmentsList.get(i).InstallInfo();
                }

            // orders.txt
            File OrderFile = new File(checkFile("orders.txt"));
            Scanner OrderScan = new Scanner(OrderFile);
            OrderScan.nextLine();
            while(OrderScan.hasNext()){
                String line = OrderScan.nextLine();
                String [] cols = line.split(",");
                int id = Integer.parseInt(cols[0].trim());
                String name = cols[1].trim();
                for (int i = 0; i<productsList.size();i++){
                    if(productsList.get(i).getProductCode().equals(cols[2].trim())){
                        String productName = productsList.get(i).getProductName();
                        break;
                    }else if(i==productsList.size()-1){
                        throw InvalidProductExcetion("Doesnt Exist\n"+line);
                    }
                }
                System.out.println("");

            }

            // File OrderErrFile = new File("orders_errors.txt")
        }catch(Exception e){System.out.println(e);}
    }

    private static Exception InvalidProductExcetion(String doesnt_Exist) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}