package uk.ac.ed.inf;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App {


    public static void main(String[] wa) throws MalformedURLException, InvalidPizzaCombinationException, ParseException {
        System.out.println("Hello World!");

        String args = "2023-01-01";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        Date date = YMD.parse(wa[1]);



        BuildNoFlyZones.buildNoFlyZones();

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));
        //get the orders
        Order[] orders = Order.getOrdersFromREST(new URL("https://ilp-rest.azurewebsites.net/orders"));

        //validate the orders
        Order[] todaysOrdersNotValid = OrderSorter.getOrdersForDay(date, orders);
        Order[] todaysOrders = OrderClient.validateOrders(todaysOrdersNotValid, participants).toArray(new Order[0]);

        //loop through and check for criteria i mentioned in report
        for (Order order : todaysOrders){
            if (order.validity == OrderOutcome.ValidButNotDelivered & Objects.equals(order.orderMaker, participants[0].name)){
                //run algorithm on delivery
                Pathfinder.droneRoute(participants[0].position, Pathfinder.droneRoute, Pathfinder.drone);
                //if too many moves, dont add
                if (Pathfinder.droneRoute.getMoves() >= 2000 | Pathfinder.realDroneRoute.getMoves()+Pathfinder.droneRoute.getMoves()>=2000) {
                    break;
                } else{
                    //add move counter and move steps to real drone
                    Pathfinder.realDroneRoute.stepDump(Pathfinder.droneRoute.getSteps());
                    order.validity = OrderOutcome.Delivered;
                }

            }


        }


        List<NoFlyZone> zones = Arrays.asList(BuildNoFlyZones.noFlyZones);
        ToGEOJSON.writeToGEOJSON(Pathfinder.droneRoute, zones, participants);
        //write to geoJSON

        // have run out of time to fix anything or get the other jsons written. its been a long 28 hours


    }
}
