package uk.ac.ed.inf;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Order {

    @JsonProperty ("orderNo")
    String orderNo;

    @JsonProperty ("orderDate")
    Date orderDate;

    @JsonProperty ("customer")
    String customer;

    @JsonProperty ("creditCardNumber")
    String creditCardNumber;

    @JsonProperty ("creditCardExpiry")
    String creditCardExpiry;

    @JsonProperty ("cvv")
    String cvv;

    @JsonProperty ("priceTotalInPence")
    int priceTotalInPence;

    @JsonProperty ("orderItems")
    ArrayList<String> orderItems;

    OrderOutcome validity = OrderOutcome.ValidButNotDelivered;


    //the name of the restaurant that makes the first pizza in the order
    public String orderMaker;
    //the menu of the orderMaker restaurant
    public List<Menu> makerMenu = new ArrayList<Menu>();
    //the cost of the order initialised to the delivery fee (100 pence)
    public int orderCost = 100;
    public boolean validOrder = false;



    static public Order[] getOrdersFromREST(URL endpoint){
        TestREST.main(endpoint);
        return TestREST.orders;
    }



    public boolean numCheck(ArrayList<String> orderItems){
        return orderItems.size() > 0 & orderItems.size() < 5;
    }

    public boolean cardNumCheck(String cardNumber){
            return cardNumber.length() < 20 & cardNumber.length() > 15 & LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(cardNumber);
    }

    public boolean cvvCheck(String cvv) {
        return (cvv.matches("[0-9]+") && (cvv.length() == 3 || cvv.length() == 4));
    }

    public boolean cardExpiryCheck(Order order) throws ParseException {
        //reformat creditCardExpiry to usable date format
        String month = order.creditCardExpiry.substring(0,2);
        String year = order.creditCardExpiry.substring(3,5);
        String newDate = ("20"+year+"-"+month+"-"+"01");
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        Date cardDate = YMD.parse(newDate);

        // check if order was made before card expired
        return order.orderDate.before(cardDate);
    }

    // checks if all pizzas are from same supplier ie valid order, and also calculates deliveryCost
    public boolean sameSupplierCalcCost(Restaurant[] participants, ArrayList<String> orderItems){



            //Method: First nested loop is finding the restaurant that makes the first pizza in the order list (orderMaker)
            //        Since all pizzas in order must be from the same restaurant, we can compare the subsequent pizzas to see if
            //        they are on the menu of the orderMaker. If any one of them is not, the order is invalid and we throw an exception.
            //        This is done in the second nested loop.


            //find the restaurant of the first order
            findMaker:
            //for each restaurant
            for (Restaurant rest : participants){
                //for each item on current restaurant's menu
                for (Menu menuItem : rest.getMenu()){
                    //check if name of first pizza in order matches current menu item
                    if (Objects.equals(menuItem.name, orderItems.get(0))){
                        orderMaker = rest.name;
                        //store the menu of orderMaker for easier access later
                        makerMenu = Arrays.asList(rest.getMenu());
                        //add cost of first pizza while we are here
                        orderCost += menuItem.priceInPence;
                        //if only one item in order then job done:
                        if (orderItems.size() == 1){return true;}

                        break findMaker;
                    }
                }
            }

            // check each of the pizzas in the order to see if they're from the same restaurant as the first.
            //start loop from 1 since we have already done the first pizza in the order array
            for (int i=1; i<orderItems.size(); i++){

                //for each item on the menu
                for (Menu menuItem : makerMenu) {

                    //check if pizza name matches current menu item

                        if (!Objects.equals(orderItems.get(i), menuItem.name)) {
                            validOrder = false;
                        } else {
                            orderCost += menuItem.priceInPence;
                            validOrder = true;
                            //we can break since we have found the pizza on the menu, and we can move on to next pizza
                            break;
                            //else will check every item on menu and if no match then invalid order
                        }


                }
                 if (!validOrder) {return false;}

            }return validOrder;

    }

}
