package uk.ac.ed.inf;
import java.lang.reflect.Member;
import java.net.URL;
import com.fasterxml.jackson.annotation.JsonProperty ;

public class Restaurant {

    /**
     * Define fields for deserialisation of restaurants JSON data.
     */
    @JsonProperty ("name")
    String name;
    @JsonProperty ("longitude")
    double longitude;
    @JsonProperty ("latitude")
    double latitude;

    //menu field is an array of the menu items for the current restaurant
    @JsonProperty ("menu")
    Menu[] menu;


    public LngLat position;

    public double pathLength;



    /**
     * Runs the main of RestaurantClient class, passing it the address of the target endpoint
     *
     * @param serverBaseAddress
     * @return RestaurantClient's array of restaurants populated with the data from the serverBaseAddress.
     */
    static Restaurant[] getRestaurantsFromRestServer(URL serverBaseAddress){

        RestaurantClient.main(serverBaseAddress);
        return RestaurantClient.restaurants;
    }

    /**
     *
     * @return Returns the menu of the restaurant in the form of an array of Menu, representing the items on the menu.
     */
    public Menu[] getMenu(){
        return menu;
    }






}
