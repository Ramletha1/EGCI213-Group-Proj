// Project 1

// * Check file path *

import java.io.*;
import java.util.*;

class FileName {
    public static final String PATH = "";                           // for VSCode
    // public static final String PATH = "src/main/Java/";          // for NetBeans
    public static final String PRODUCT = "products.txt";
    public static final String INSTALLMENT = "installments.txt";
    public static final String ORDER = "orders.txt";
    // public static final String ORDER = "orders_errors.txt";
}

class InvalidInputException extends Exception {
    public InvalidInputException(String message){
        super(message);
    }
}

// From products.txt
class Product implements Comparable<Product>{
    // Original from file
    private String product_code;
    private String product_name;
    private int product_unitprice;

    private int product_totalCash;
    private int product_totalUnit;

    public Product(String product_code, String product_name, int product_unitprice){
        this.product_code = product_code;
        this.product_name = product_name;
        this.product_unitprice = product_unitprice;
        this.product_totalCash = 0;
        this.product_totalUnit = 0;
    }

    // Area for method
    public int compareTo(Product other){
        if (this.product_totalUnit == other.product_totalUnit)
            return this.product_name.compareToIgnoreCase(other.product_name);   // Alphabetical Order
        else
            return other.product_totalUnit - this.product_totalUnit;            // Decreasing Order
    }

    public void ProductInfo(){
        System.out.printf("%-15s (%s) unit price = %,6d\n", product_name, product_code, product_unitprice);
    }

    public String getProductCode(){
        return product_code;
    }

    public String getProductName(){
        return product_name;
    }

    public int getProductPrice(){
        return product_unitprice;
    }

    public void addSales(int unit){
        product_totalUnit += unit;
        product_totalCash += (unit*product_unitprice);

    }

    public int getTotal(int x){
        if (x==0) return product_totalUnit;
        return product_totalCash;
    }

}



// From orders.txt + product_name
class Order{
    // Original from file
    private int order_id;
    private Customer order_name;
    private Product product_name;
    private int order_unit;
    private int order_plan;


    // Area for method
    public Order(int order_id, Customer order_name, Product product_name, int order_unit, int order_plan){
        this.order_id = order_id;
        this.order_name = order_name;
        this.product_name = product_name;
        this.order_unit = order_unit;
        this.order_plan = order_plan;
    }

    public String getProduct() {
        return product_name.getProductName();
    }

    public String getName() {
        return order_name.getName();
    }

    public int getID() {
        return order_id;
    }

    public void OrderInfo(){
        System.out.printf("Order %2d >> %-5s  %-14s x %2d  %3d-month installments\n",order_id, order_name.getName(), product_name.getProductName(), order_unit, order_plan);
    }

    public void OrderProcess(ArrayList<Installment> installmentsList){
        float subtotal1 = product_name.getProductPrice() * order_unit;
        int points_earn = (int) (subtotal1 / 500);

        product_name.addSales(order_unit);

        float discount = 0;
        boolean usedPoints = false; 
        int currentPoints = order_name.getPoints();
        
        if (currentPoints >= 100) {
            discount = subtotal1 * 0.05f; 
            order_name.addPoints(-100); 
            usedPoints = true;
        } else if (currentPoints == 0) {
            discount = 200;
        }  
      

        float subtotal2 = subtotal1 - discount;
        float totalInterest = 0;
        float totalPayment = subtotal2;
        float monthlyPayment = 0;

        if (order_plan > 0) {
            for (Installment inst : installmentsList) {
                if (inst.getMonth() == order_plan) {
                    totalInterest = (subtotal2 * (float) inst.getInterest() * order_plan) / 100;
                    totalPayment = subtotal2 + totalInterest;
                    monthlyPayment = totalPayment / order_plan;
                    break;
                }
            }   
        }

    System.out.printf("%2d. %-5s (%,6d pts) ", order_id, order_name.getName(), currentPoints);
    System.out.printf("order    = %-14s x %2d   ", product_name.getProductName(), order_unit);
    System.out.printf("sub-total(1)   = %,10.2f  ", subtotal1);
    System.out.printf("(+ %,5d pts next order)\n", points_earn);

    System.out.printf(" ".repeat(23) + "discount = %,10.2f ", discount);
    System.out.printf(" ".repeat(11) + "sub-total(2)   = %,10.2f ", subtotal2);
    System.out.printf("%s \n", (usedPoints == true) ? " (-   100 pts)" : "");

    System.out.printf(" ".repeat(23) + "%-25s", (order_plan > 0) ? order_plan + "-months installments" : "full payment");
    System.out.printf(" ".repeat(8) + "%s\n", (order_plan > 0) ? "total interest = " + String.format("%,10.2f", totalInterest) + " " : "");
    System.out.printf(" ".repeat(23) + "total    = %,10.2f ", totalPayment);
    System.out.printf(" ".repeat(11) + "%s \n", (order_plan > 0) ? "monthly total  = " + String.format("%,10.2f", monthlyPayment) : "");

    System.out.println("");

    order_name.addPoints(points_earn);
    }
}

// From orders.txt + order_name
class Customer implements Comparable<Customer>{
    // Original from file
    private String order_name;
    // Extra requirement
    private int order_point;

    // Area for method
    public Customer(String name,int point){
        this.order_name = name;
        this.order_point = point;
    }

    public int compareTo(Customer other) {
        if (this.order_point == other.order_point)
            return this.order_name.compareToIgnoreCase(other.order_name);   // Alphabetical Order
        else
            return other.order_point - this.order_point;                    // Decreasing Order
    }

    public String getName(){
        return order_name;
    }

    public int getPoints(){
        return order_point;
    }

    public void addPoints(int num){
        order_point += num;
    }
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

    public int getMonth() {  
        return month;
    }

    public double getInterest() {  
        return interest;
    }

    public void InstallInfo(){
        System.out.printf("%2d-month plans    monthly interest = %.2f %%\n",month, interest);
    }
}



public class Main {

    // According to the requirement, errors locate in 'orders_errors.txt' only
    public static void CheckingNumber (int[] number) throws InvalidNumberException{
        // int[] number = {order_id, order_unit, order_plan};
    }

    public static void CheckingName (String[] name) throws InvalidNameException{
        // String[] name = {order_name, order_code};
    }

    // For main, didn't add try catch yet
    public static void main (String[] args) {
        Scanner userInput = new Scanner(System.in);
        ArrayList<Product> productsList = new ArrayList<Product>();
        ArrayList<Customer> customersList = new ArrayList<Customer>();
        ArrayList<Installment> installmentsList = new ArrayList<Installment>();
        ArrayList<Order> orderList = new ArrayList<Order>();

        // Scanner

        // products.txt
        File ProductFile = new File(FileName.PRODUCT);
        while (true) {
            try (Scanner ProductScan = new Scanner(ProductFile)) {
                ProductScan.nextLine();
                while (ProductScan.hasNext()) {
                    String line = ProductScan.nextLine();
                    String[] cols = line.trim().split("\\s*,\\s*");
                    String code = cols[0];
                    String productName = cols[1];
                    int productPrice = Integer.parseInt(cols[2]);

                    productsList.add(new Product(code,productName,productPrice));
                } break;
            } catch (Exception e) {
                System.err.println(e);
                System.out.println("New File Name = ");
                ProductFile = new File(userInput.nextLine().trim());
                continue;
            }
        }
        System.out.println("Read from " + ProductFile.getPath());
        
        for (Product product : productsList){
            //productsList.get(i).ProductInfo();
            product.ProductInfo();
        }
        
        System.out.println("");

        // installments.txt
        File InstallFile = new File(FileName.INSTALLMENT);
        while (true) {
            try (Scanner InstallScan = new Scanner(InstallFile)) {        
                InstallScan.nextLine();
                while(InstallScan.hasNext()) {
                    String line = InstallScan.nextLine();
                    String [] cols = line.trim().split("\\s*,\\s*");
                    int months = Integer.parseInt(cols[0]);
                    double interest = Double.parseDouble(cols[1]);
                    installmentsList.add(new Installment(months,interest));
                }
                break;
            } catch (FileNotFoundException e) {
                System.err.println(e);
                System.out.println("New File Name =");
                InstallFile = new File(userInput.nextLine().trim());
                continue;
            } catch (Exception e) { System.err.println(e); }
        }
        System.out.println("Read from " + InstallFile.getPath());
        
        for(Installment installment : installmentsList){
            installment.InstallInfo();
        }
        System.out.println();

        // orders.txt
        File OrderFile = new File(FileName.ORDER);
        while (true) {
            try (Scanner OrderScan = new Scanner(OrderFile)) {
                OrderScan.nextLine();
                while (OrderScan.hasNext()) {
                    String line = "";
                    try {
                        line = OrderScan.nextLine();
                        String[] cols = line.trim().split("\\s*,\\s*");
                        int id = Integer.parseInt(cols[0]);
                        String name = cols[1];
                        Customer current_customer = new Customer("EMPTY",0);
                        int x = 0;
                        do {
                            if(customersList.size()==0){
                                current_customer = new Customer(name,0);
                                customersList.add(current_customer);

                                break;
                            }else if(customersList.get(x).getName().equals(name)){
                                current_customer = customersList.get(x);
                                break;

                            }else if(x==customersList.size()-1){
                                current_customer = new Customer(name,0);
                                customersList.add(current_customer);
                                break;

                            }
                            x++;
                        } while (x<customersList.size());

                        Product productOrder = new Product("EMPTY","E",0);
                        String productCode = cols[2];
                        int unit = Integer.parseInt(cols[3]);
                        int plan = Integer.parseInt(cols[4]);

                        for (int i = 0; i<productsList.size();i++){
                            if (productsList.get(i).getProductCode().equals(productCode)){
                                productOrder = productsList.get(i);   
                                break;
                            } else if (i==productsList.size()-1) throw new InvalidInputException("For product: \"" + productCode + "\"");
                        }
                        if (unit < 0) throw new InvalidInputException("For units: \"" + unit + "\"");

                        for (int i = 0; i<installmentsList.size(); i++) {
                            if (installmentsList.get(i).getMonth() == plan) break;
                            else if (i==installmentsList.size()-1) throw new InvalidInputException("For installment plan: \"" + plan + "\"");
                        }


                        orderList.add(new Order(id,current_customer,productOrder,unit,plan));
                    } catch (Exception e) {
                        System.err.println(e);
                        System.out.printf("%-40s <-- Skip this line\n\n", line);
                        continue;
                    }
                }
                break;
            } catch (FileNotFoundException e) {
                System.err.println(e);
                System.out.println("New File Name =");
                OrderFile = new File(userInput.nextLine().trim());
                continue;
            }
        }
        System.out.println("Read from " + OrderFile.getPath());

        for(Order order : orderList){
            order.OrderInfo();
        }
        System.out.println("\n=== Order processing ===");
        for(Order order : orderList){
            order.OrderProcess(installmentsList);
        }
        Collections.sort(productsList);
        Collections.sort(customersList);

        System.out.println("\n=== Product summary ===");

        for(Product product : productsList){
            ArrayList<Order> luckyList = new ArrayList<Order>();
            for (Order order : orderList) {
                if (order.getProduct() == product.getProductName()) luckyList.add(order);
            }
            Random random = new Random();
            int lucky = random.nextInt(luckyList.size());
            System.out.printf("%-15s total sales = %3d units  =  %,12.2f THB   ",product.getProductName(),product.getTotal(0),(float)product.getTotal(1));
            System.out.printf("lucky draw winner = %7s (order %2d)\n", luckyList.get(lucky).getName(), luckyList.get(lucky).getID());
        }
    
        System.out.println("\n=== Customer summary ===");
    
        for(Customer customer : customersList){
            System.out.printf("%-7s remaining points = %,6d \n",customer.getName(),customer.getPoints());
        }
        userInput.close();
    }
}