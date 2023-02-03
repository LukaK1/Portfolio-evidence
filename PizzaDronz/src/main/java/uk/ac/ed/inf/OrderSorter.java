package uk.ac.ed.inf;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class for functions manipulating orders for specific day
 */
public class OrderSorter {


    public static Order[] getOrdersForDay(Date date, Order[] orders) {

        ArrayList<Order> todaysOrders = new ArrayList<>();

        //get those from this date
        for (Order order : orders) {
            if (order.orderDate.equals(date)) {
                todaysOrders.add(order);
            }
        }

        //turn into Array
        Order[] ordersToday = new Order[todaysOrders.size()];

        for (int i = 0; i < todaysOrders.size(); i++){

            ordersToday[i] = todaysOrders.get(i);

        }
        return ordersToday;

    }




/*
    public static ArrayList<Order> shortestFirst(ArrayList<Order> orderList) {
        for (Order order : orderList) {

            if (order.validity == OrderOutcome.ValidButNotDelivered) {
                order.path = Pathfinder.getClosest(Pathfinder.allPolyIntersects(appleton, order.r))
            }

        }
    }
    */
}








