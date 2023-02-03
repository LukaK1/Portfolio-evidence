package uk.ac.ed.inf;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;


public class TestREST {

    static Order[] orders;
    static Restaurant[] restaurants;
    public static void main(URL argURL){


        ObjectMapper mapper = new ObjectMapper();

        if (Objects.equals(argURL.toString(), "https://ilp-rest.azurewebsites.net/orders")){

            /** get JSON data and deserialise it to the specified format class and write the result to the restaurants array */
            try{
                // restaurants = mapper.readValue(new URL(argURL + "/") , Restaurant[].class ) ;
                orders = mapper.readValue(new URL("https://ilp-rest.azurewebsites.net/orders") , Order[].class );
            }
            // handle exception
            catch (IOException e) {
                e.printStackTrace();
            }

        } else if (Objects.equals(argURL.toString(), "https://ilp-rest.azurewebsites.net/restaurants")){

            /** get JSON data and deserialise it to the specified format class and write the result to the restaurants array */
            try{
                // restaurants = mapper.readValue(new URL(argURL + "/") , Restaurant[].class ) ;
                restaurants = mapper.readValue(new URL("https://ilp-rest.azurewebsites.net/restaurants") , Restaurant[].class );
            }
            // handle exception
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public Order[] getOrders(){
        return orders;
    }

    public Restaurant[] getRestaurants(){
        return restaurants;
    }


}
