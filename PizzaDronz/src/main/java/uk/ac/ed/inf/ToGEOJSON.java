package uk.ac.ed.inf;
import com.mapbox.geojson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ToGEOJSON {


    public static void writeToGEOJSON(DroneRoute dronePath, List<NoFlyZone> zones, Restaurant[] restaurants){



        //Drone path
        List<Point> dronePathToWrite = new ArrayList<>();

        dronePathToWrite.add(Point.fromLngLat(DroneRoute.steps.get(0).A().Lng(), DroneRoute.steps.get(0).A().Lat()));
        for (Line step : DroneRoute.steps){
            dronePathToWrite.add(Point.fromLngLat(step.B().Lng(), step.B().Lat()));
        }
        Geometry dronePathGeom = LineString.fromLngLats(dronePathToWrite);
        Feature dronePathGeoJSON = Feature.fromGeometry(dronePathGeom);




        //No Fly zones
        List<Feature> JsonZones = new ArrayList<>();

        for (NoFlyZone zone : zones){

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


        //Restaurant locations
        List<Feature> restLocations = new ArrayList<>();

        for (Restaurant rest : restaurants){
            Geometry restPoint = Point.fromLngLat(rest.position.Lng(), rest.position.Lat());
            restLocations.add(Feature.fromGeometry(restPoint));
        }

        //Appleton tower
        Geometry appleT = Point.fromLngLat(Pathfinder.appleton.Lng(), Pathfinder.appleton.Lat());
        Feature GeoAppleT = Feature.fromGeometry(appleT);


        List<Feature> featureList = new ArrayList<>();

        featureList.add(GeoAppleT);
        featureList.addAll(restLocations);
        featureList.addAll(JsonZones);
        featureList.add(dronePathGeoJSON);

        FeatureCollection finalGeoJSON = FeatureCollection.fromFeatures(featureList);



        try {
            FileWriter myWriter = new FileWriter("output.geojson");
            myWriter.write(finalGeoJSON.toJson());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }




    }


}
