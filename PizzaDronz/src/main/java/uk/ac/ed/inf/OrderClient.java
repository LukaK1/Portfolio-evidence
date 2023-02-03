package uk.ac.ed.inf;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.text.ParseException;

public class OrderClient {


    static Order[] orders = TestREST.orders;
    static ArrayList<Order> validOrders = new ArrayList<Order>();


    static public ArrayList<Order> validateOrders(Order[] orders, Restaurant[] participants) throws ParseException {
        for (Order order : orders){
            if (!order.numCheck(order.orderItems)){
                order.validity = OrderOutcome.InvalidPizzaCount;

            } if (!order.cardNumCheck(order.creditCardNumber)) {
                order.validity = OrderOutcome.InvalidCardNumber;

            } if (!order.cvvCheck(order.cvv)){
                order.validity = OrderOutcome.InvalidCvv;

            } if (!order.cardExpiryCheck(order)){
                order.validity = OrderOutcome.InvalidExpiryDate;

            } if (!order.sameSupplierCalcCost(participants, order.orderItems)){
                order.validity = OrderOutcome.InvalidPizzaCombinationMultipleSuppliers;
            }
            if (order.validity == OrderOutcome.ValidButNotDelivered) {
                validOrders.add(order);
            }

        }
        return validOrders;
    }


}


