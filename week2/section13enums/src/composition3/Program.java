package composition3;

import composition3.model.entities.Client;
import composition3.model.entities.Order;
import composition3.model.entities.OrderItem;
import composition3.model.entities.Product;
import composition3.model.enums.OrderStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws ParseException {
        Locale.setDefault(Locale.US);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter client data: ");
        System.out.println("Name: ");
        String name = sc.nextLine();
        System.out.println("Email: ");
        String email = sc.nextLine();
        System.out.println("Birth date: dd/mm/yyyy");
        Date birthDate = sdf.parse(sc.next());

        Client client = new Client(name, email, birthDate);

        System.out.println("Enter order data: ");
        System.out.println("Status: ");
        OrderStatus os = OrderStatus.valueOf(sc.next());
        System.out.println("How many items to this order? ");
        Integer itemQuantity = sc.nextInt();
        Order order = new Order(new Date(), os, client);

        for(int i = 0; i < itemQuantity; i++){
            System.out.println("Product data: ");
            System.out.println("Name: ");
            sc.nextLine();
            String productName = sc.nextLine();
            System.out.println("Price: ");
            Double productPrice = sc.nextDouble();
            System.out.println("Quantity: ");
            Integer productQuantity = sc.nextInt();

            Product product = new Product(productName, productPrice);
            OrderItem orderItem = new OrderItem(productQuantity, product);
            order.addItem(orderItem);
        }
        sc.close();
        System.out.println(order);
    }
}
