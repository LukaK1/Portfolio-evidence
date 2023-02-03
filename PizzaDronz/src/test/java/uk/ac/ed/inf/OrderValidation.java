package uk.ac.ed.inf;

import org.junit.Test;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderValidation {


    //============================== SAME RESTAURANT CHECK ====================================
    @Test
    public void sameRestaurant2Valid() throws MalformedURLException, ParseException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-06";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        assert testOrder.sameSupplierCalcCost(participants,testOrder.orderItems);


    }

    @Test
    public void sameRestaurant1Valid() throws MalformedURLException, ParseException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-06";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Meat Lover"));

        assert testOrder.sameSupplierCalcCost(participants,testOrder.orderItems);


    }

    @Test
    public void sameRestaurant2Invalid() throws MalformedURLException, ParseException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-06";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Meat Lover"));

        assert !testOrder.sameSupplierCalcCost(participants,testOrder.orderItems);


    }

    @Test
    public void sameRestaurant2Valid1Invalid() throws MalformedURLException, ParseException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-06";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone", "Meat Lover"));

        assert !testOrder.sameSupplierCalcCost(participants,testOrder.orderItems);


    }
    //------------------------------------------------------------------------------------------




    //============================== CARD NUMBER CHECK =========================================

    @Test
    public void validCardNumber() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-27";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        assert testOrder.cardNumCheck(testOrder.creditCardNumber);

    }

    @Test
    public void invalidCardNumber() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-27";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123";
        testOrder.creditCardNumber = "4539355633472015";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        assert !testOrder.cardNumCheck(testOrder.creditCardNumber);

    }

    @Test
    public void shortCardNumber() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-27";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123";
        testOrder.creditCardNumber = "4539355";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        assert !testOrder.cardNumCheck(testOrder.creditCardNumber);

    }

    @Test
    public void longCardNumber() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-27";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123";
        testOrder.creditCardNumber = "4539355633472051525590909";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        assert !testOrder.cardNumCheck(testOrder.creditCardNumber);

    }


    //-----------------------------------------------------------------------------------------




    //=============================== CVV CHECK ===============================================

    @Test
    public void threeDigitCVV() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-27";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        assert testOrder.cvvCheck(testOrder.cvv);

    }

    @Test
    public void fourDigitCVV() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-27";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "1234";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        assert testOrder.cvvCheck(testOrder.cvv);

    }

    @Test
    public void fiveDigitCVV() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-27";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "12345";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        assert !testOrder.cvvCheck(testOrder.cvv);

    }

    @Test
    public void twoDigitCVV() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-27";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "12";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        assert !testOrder.cvvCheck(testOrder.cvv);

    }

    @Test
    public void twoDigitOneSymbolCVV() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-27";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "12@";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        assert !testOrder.cvvCheck(testOrder.cvv);

    }

    @Test
    public void threeDigitOneSymbolCVV() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-27";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123@";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        assert !testOrder.cvvCheck(testOrder.cvv);

    }


    //-----------------------------------------------------------------------------------------





    //============================== NUMBER OF ITEMS CHECK ====================================

    @Test
    public void oneItem() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-27";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita"));

        assert testOrder.numCheck(testOrder.orderItems);

    }

    @Test
    public void fourItems() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-27";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone", "Margarita", "Calzone"));

        assert testOrder.numCheck(testOrder.orderItems);;

    }

    @Test
    public void fiveItems() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-27";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone", "Margarita", "Calzone", "Calzone"));

        assert !testOrder.numCheck(testOrder.orderItems);;

    }

    @Test
    public void noItems() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-27";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of());

        assert !testOrder.numCheck(testOrder.orderItems);;

    }

    //-----------------------------------------------------------------------------------------




    //============================= CARD EXPIRY CHECK =======================================

    @Test
    public void inDateCheck() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-03";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        assert testOrder.cardExpiryCheck(testOrder);

    }

    @Test
    public void outOfDateCheck() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-03-03";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "02/23";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        assert !testOrder.cardExpiryCheck(testOrder);

    }

    @Test
    public void expiresSameMonthDateCheck() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2024-09-15";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        assert !testOrder.cardExpiryCheck(testOrder);

    }

    @Test
    public void expiresSameDayDateCheck() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2024-09-01";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        assert !testOrder.cardExpiryCheck(testOrder);

    }

    @Test
    public void expiresDayBeforeDateCheck() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2024-08-31";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        assert testOrder.cardExpiryCheck(testOrder);

    }

    //---------------------------------------------------------------------------------------



    //============================== COMBINATION CHECKS =====================================

    @Test
    public void validOrder() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-03";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        Order[] orders = new Order[1];
        orders[0] = testOrder;


        assert OrderClient.validateOrders(orders, participants).contains(testOrder);
        assert OrderClient.validateOrders(orders, participants).get(0).validity == OrderOutcome.ValidButNotDelivered;

    }

    @Test
    public void allChecksInvalidOrder() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-03";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123@";
        testOrder.creditCardNumber = "45393556334725";
        testOrder.creditCardExpiry = "09/22";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone", "Margarita", "Margarita", "Calzone"));

        Order[] orders = new Order[1];
        orders[0] = testOrder;

        //assert OrderClient.validateOrders(orders, participants).get(0).validity != OrderOutcome.ValidButNotDelivered;

        OrderClient.validateOrders(orders, participants);
        assert testOrder.validity != OrderOutcome.ValidButNotDelivered;


    }

    @Test
    public void invalidNumberOfItemsOrder() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-03";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone", "Margarita", "Margarita", "Calzone"));

        Order[] orders = new Order[1];
        orders[0] = testOrder;

        OrderClient.validateOrders(orders, participants);
        assert testOrder.validity == OrderOutcome.InvalidPizzaCount;

    }

    @Test
    public void invalidCardNumberOrder() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-03";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123";
        testOrder.creditCardNumber = "453935563347205390";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        Order[] orders = new Order[1];
        orders[0] = testOrder;

        OrderClient.validateOrders(orders, participants);
        System.out.println(testOrder.validity);
        assert testOrder.validity == OrderOutcome.InvalidCardNumber;

    }

    @Test
    public void invalidCVVOrder() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-03";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "123@@";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        Order[] orders = new Order[1];
        orders[0] = testOrder;

        OrderClient.validateOrders(orders, participants);
        assert testOrder.validity == OrderOutcome.InvalidCvv;

    }

    @Test
    public void invalidExpiryOrder() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-05-03";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "1234";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "02/23";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        Order[] orders = new Order[1];
        orders[0] = testOrder;

        OrderClient.validateOrders(orders, participants);
        assert testOrder.validity == OrderOutcome.InvalidExpiryDate;

    }

    @Test
    public void invalidSuppliersOrder() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder = new Order();

        String args = "2023-01-03";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder.orderDate = YMD.parse(args);
        testOrder.cvv = "1234";
        testOrder.creditCardNumber = "4539355633472055";
        testOrder.creditCardExpiry = "09/24";
        testOrder.customer = "John Doe";
        testOrder.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone", "Meat Lover"));

        Order[] orders = new Order[1];
        orders[0] = testOrder;

        OrderClient.validateOrders(orders, participants);
        assert testOrder.validity == OrderOutcome.InvalidPizzaCombinationMultipleSuppliers;

    }



    //--------------------------------------------------------------------------------------





    //=============================== MULTIPLE ORDER CHECKS ================================

    @Test
    public void threeValidOrders() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder1 = new Order();

        String args1 = "2023-01-03";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder1.orderDate = YMD.parse(args1);
        testOrder1.cvv = "1234";
        testOrder1.creditCardNumber = "4539355633472055";
        testOrder1.creditCardExpiry = "09/24";
        testOrder1.customer = "John Doe";
        testOrder1.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        Order testOrder2 = new Order();

        String args2 = "2023-01-03";
        testOrder2.orderDate = YMD.parse(args2);
        testOrder2.cvv = "132";
        testOrder2.creditCardNumber = "4539052825329181";
        testOrder2.creditCardExpiry = "05/23";
        testOrder2.customer = "Micheal Jackson";
        testOrder2.orderItems = new ArrayList<String>(List.of("Meat Lover"));

        Order testOrder3 = new Order();

        String args3 = "2023-01-03";
        testOrder3.orderDate = YMD.parse(args3);
        testOrder3.cvv = "1122";
        testOrder3.creditCardNumber = "4537071352821545";
        testOrder3.creditCardExpiry = "08/25";
        testOrder3.customer = "Joe Man";
        testOrder3.orderItems = new ArrayList<String>(List.of("Calzone"));

        Order[] orders = new Order[3];
        orders[0] = testOrder1;
        orders[1] = testOrder2;
        orders[2] = testOrder3;

        ArrayList<Order> results = OrderClient.validateOrders(orders, participants);

        assert results.contains(testOrder1);
        System.out.println(testOrder2.validity);
        assert results.contains(testOrder2);
        assert results.contains(testOrder3);

    }

    @Test
    public void twoValidOneInvalidOrders() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder1 = new Order();

        String args1 = "2023-01-03";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder1.orderDate = YMD.parse(args1);
        testOrder1.cvv = "1234";
        testOrder1.creditCardNumber = "4539355633472055";
        testOrder1.creditCardExpiry = "09/24";
        testOrder1.customer = "John Doe";
        testOrder1.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        Order testOrder2 = new Order();

        String args2 = "2023-01-03";
        testOrder2.orderDate = YMD.parse(args2);
        testOrder2.cvv = "13212122";
        testOrder2.creditCardNumber = "4539052825329181";
        testOrder2.creditCardExpiry = "05/23";
        testOrder2.customer = "Micheal Jackson";
        testOrder2.orderItems = new ArrayList<String>(List.of("Meat Lover"));

        Order testOrder3 = new Order();

        String args3 = "2023-01-03";
        testOrder3.orderDate = YMD.parse(args3);
        testOrder3.cvv = "1122";
        testOrder3.creditCardNumber = "4537071352821545";
        testOrder3.creditCardExpiry = "08/25";
        testOrder3.customer = "Joe Man";
        testOrder3.orderItems = new ArrayList<String>(List.of("Calzone"));

        Order[] orders = new Order[3];
        orders[0] = testOrder1;
        orders[1] = testOrder2;
        orders[2] = testOrder3;

        ArrayList<Order> results = OrderClient.validateOrders(orders, participants);

        assert results.contains(testOrder1);
        assert !results.contains(testOrder2);
        assert results.contains(testOrder3);

    }

    @Test
    public void oneValidTwoInvalidOrders() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder1 = new Order();

        String args1 = "2023-01-03";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder1.orderDate = YMD.parse(args1);
        testOrder1.cvv = "1234";
        testOrder1.creditCardNumber = "4539355633472055";
        testOrder1.creditCardExpiry = "09/24";
        testOrder1.customer = "John Doe";
        testOrder1.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        Order testOrder2 = new Order();

        String args2 = "2023-01-03";
        testOrder2.orderDate = YMD.parse(args2);
        testOrder2.cvv = "132";
        testOrder2.creditCardNumber = "4539052825329181";
        testOrder2.creditCardExpiry = "05/23";
        testOrder2.customer = "Micheal Jackson";
        testOrder2.orderItems = new ArrayList<String>(List.of("Meat Lover", "Margarita"));

        Order testOrder3 = new Order();

        String args3 = "2023-01-03";
        testOrder3.orderDate = YMD.parse(args3);
        testOrder3.cvv = "1122";
        testOrder3.creditCardNumber = "45370713528215451111111111";
        testOrder3.creditCardExpiry = "08/25";
        testOrder3.customer = "Joe Man";
        testOrder3.orderItems = new ArrayList<String>(List.of("Calzone"));

        Order[] orders = new Order[3];
        orders[0] = testOrder1;
        orders[1] = testOrder2;
        orders[2] = testOrder3;

        ArrayList<Order> results = OrderClient.validateOrders(orders, participants);

        assert results.contains(testOrder1);
        assert !results.contains(testOrder2);
        assert !results.contains(testOrder3);

    }

    @Test
    public void threeInvalidOrders() throws ParseException, MalformedURLException {

        //get the restaurants
        Restaurant rest = new Restaurant();
        Restaurant[] participants = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));

        Order testOrder1 = new Order();

        String args1 = "2023-01-03";
        SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
        testOrder1.orderDate = YMD.parse(args1);
        testOrder1.cvv = "12341111";
        testOrder1.creditCardNumber = "4539355633472055";
        testOrder1.creditCardExpiry = "09/24";
        testOrder1.customer = "John Doe";
        testOrder1.orderItems = new ArrayList<String>(List.of("Margarita", "Calzone"));

        Order testOrder2 = new Order();

        String args2 = "2023-01-03";
        testOrder2.orderDate = YMD.parse(args2);
        testOrder2.cvv = "132244";
        testOrder2.creditCardNumber = "4539052825329181";
        testOrder2.creditCardExpiry = "01/23";
        testOrder2.customer = "Micheal Jackson";
        testOrder2.orderItems = new ArrayList<String>(List.of("Meat Lover"));

        Order testOrder3 = new Order();

        String args3 = "2023-01-03";
        testOrder3.orderDate = YMD.parse(args3);
        testOrder3.cvv = "1122";
        testOrder3.creditCardNumber = "453707135282154500000000008";
        testOrder3.creditCardExpiry = "08/25";
        testOrder3.customer = "Joe Man";
        testOrder3.orderItems = new ArrayList<String>(List.of("Calzone"));

        Order[] orders = new Order[3];
        orders[0] = testOrder1;
        orders[1] = testOrder2;
        orders[2] = testOrder3;

        ArrayList<Order> results = OrderClient.validateOrders(orders, participants);

        assert !results.contains(testOrder1);
        assert !results.contains(testOrder2);
        assert !results.contains(testOrder3);

    }



    //--------------------------------------------------------------------------------------



}
