package uk.ac.ed.inf;
import com.fasterxml.jackson.annotation.JsonProperty;


public class CentralAreaPoint {
    @JsonProperty ( "name" )
    public String name ;
    @JsonProperty ( "longitude" )
    public static double longitude;
    @JsonProperty ( "latitude" )
    public static double latitude;

    public String getName(){
      return name;
    }


    public Double getLongitude(){
     return longitude;
    }
    public Double getLatitude(){
      return latitude;
    }

}
