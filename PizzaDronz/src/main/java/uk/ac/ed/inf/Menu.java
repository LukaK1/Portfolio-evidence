package uk.ac.ed.inf;
import com.fasterxml.jackson.annotation.JsonProperty ;

public class Menu {


    /**
     * Define fields for deserialisation of the menus in the restaurants JSON data.
     */
    @JsonProperty ("name")
    String name;
    @JsonProperty ("priceInPence")
    int priceInPence;

}
