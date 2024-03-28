package composition3.model.entities;
import composition3.model.enums.OrderStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private Date moment;
    private OrderStatus status;
    private List<OrderItem> items = new ArrayList<>();

    private Client client;

    private static StringBuilder builder = new StringBuilder();
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public Order(Date moment, OrderStatus status, Client client) {
        this.moment = moment;
        this.status = status;
        this.client = client;
    }

    public Date getMoment() {
        return moment;
    }

    public void setMoment(Date moment) {
        this.moment = moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

     public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void addItem(OrderItem item){
        items.add(item);
    }

    public void removeItem(OrderItem item){
        items.remove(item);
    }

    public Double total(){
        Double total = 0.0;
        for (OrderItem item : items){
            total += item.subTotal();
        }
        return total;
    }

    @Override
    public String toString(){
        builder.append("ORDER SUMARY: \n");
        builder.append("Order moment: " + sdf.format(this.moment) + "\n");
        builder.append("Order status: " + this.status + "\n");
        builder.append("Client: " + this.getClient() + "\n");
        builder.append("Order items: \n");
        for (OrderItem oi : items){
            builder.append(oi + "\n");
        }
        builder.append("Total price: $" + String.format("%.2f", this.total()));

        return builder.toString();
    }
}
