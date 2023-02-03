package uk.ac.ed.inf;

import com.mapbox.geojson.*;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NoFlyZoneTests {

    @Test
    public void NoFlyZoneRESTTest(){

        //I cannot figure out how to create my own REST server files for testing different no-fly-zones but
        //if I could i would expand these tests by adding a parameter to the buildNoFlyZones() method which would be a URL
        //to the desired REST file for more exhaustive testing. Currently, this test only applies to the supplied
        //set of no-fly-zones.
        BuildNoFlyZones.buildNoFlyZones();

        //No Fly zones
        List<Feature> JsonZones = new ArrayList<>();

        for (NoFlyZone zone : BuildNoFlyZones.noFlyZones){

            List<List<Point>> thisZone = new ArrayList<>();
            int x = 0;
            List<Point> line = new ArrayList<>();
            for (int i = 0; i<zone.coords.size(); i++){


                line.add(Point.fromLngLat(zone.coords.get(i).Lng(), zone.coords.get(i).Lat()));
                //  line.add(Point.fromLngLat(zone.coords.get(i+1).Lng(), zone.coords.get(i+1).Lat()));
                thisZone.add(line);
                x = i;
            }

            Geometry GeoZone = Polygon.fromLngLats(thisZone);
            JsonZones.add(Feature.fromGeometry(GeoZone));
        }
        List<Feature> featureList = new ArrayList<>();
        featureList.addAll(JsonZones);
        FeatureCollection finalGeoJSON = FeatureCollection.fromFeatures(featureList);

        try {
            FileWriter myWriter = new FileWriter("noFlyZoneTestOutput.geojson");
            myWriter.write(finalGeoJSON.toJson());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        /**
            SEE testOutput.geojson FILE IN THE REPO FOR THE RESULT
            SEE geojsonVisualised.PNG FILE IN THE REPO TO SEE THE RESULT

         */
    }




}
