package uk.ac.ed.inf;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

    public class RestaurantClient {

        //Instantiate the empty list of restaurants
    static Restaurant[] restaurants;

         /**bn
         * main executes the try statement which reads the JSON file from the end point and deserialises it and stores it in
         * the static restaurants array.
         * @param argURL
         */
    public static void main(URL argURL){

        ObjectMapper mapper = new ObjectMapper();

        /** get JSON data and deserialise it to the specified format class and write the result to the restaurants array */
        try{
           // restaurants = mapper.readValue(new URL(argURL + "/") , Restaurant[].class ) ;
                 restaurants = mapper.readValue(new URL("https://ilp-rest.azurewebsites.net/restaurants") , Restaurant[].class ) ;

        }
        // handle exception
            catch (IOException e) {
                    e.printStackTrace();
                }

        for (Restaurant restaurant : restaurants){
            restaurant.position = new LngLat(restaurant.longitude, restaurant.latitude);
        }


    }




}
