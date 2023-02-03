package uk.ac.ed.inf;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class BuildNoFlyZones {


    static NoFlyZone[] noFlyZones;


    /**
     * Read the no fly zone data from the rest server and into the list of no fly zones
     */
    public static void buildNoFlyZones(){

        ObjectMapper mapper = new ObjectMapper();

        try {
            String baseUrl = "https://ilp-rest.azurewebsites.net/" ;

            //Read JSON data and deserialise it from centralArea
            noFlyZones = mapper.readValue(new URL("https://ilp-rest.azurewebsites.net/noFlyZones") , NoFlyZone[].class );

            //add each zone's points to its list of LngLat objects
           for ( NoFlyZone zone : noFlyZones) {

               //create LngLat object for each point in zone and add to array of LngLat for zone
               for (double[] point : zone.rawCoordinates) {
                   zone.coords.add(new LngLat(point[0], point[1]));
               }
               //add the first LngLat to the end so that an edge can be drawn to close the polygon
               zone.coords.add(zone.coords.get(0));

               //add each edge of zone to arraylist for convenience
               for (int i=0;i<(zone.coords.size()-1); i++){
                   zone.edges.add(new Line(zone.coords.get(i), zone.coords.get(i+1), null));
               }
           }






        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
