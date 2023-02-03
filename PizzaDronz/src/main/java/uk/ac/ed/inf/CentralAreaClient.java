package uk.ac.ed.inf;
import com.fasterxml.jackson.databind.ObjectMapper ;
import java.io.IOException ;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * client to GET JSON data of the central area coordinates
 */
    class CentralAreaClient {


    public static ArrayList<Line> edges;



    // private static List<Double> centralAreaXY = new ArrayList<Double>();
        //resizable 2d array for variable number of points on central area
    private static List<LngLat> centralAreaXY = new ArrayList<LngLat>();

        //create static instance of CentralAreaClient
    static CentralAreaClient inst = new CentralAreaClient();


    /**
     * Reads JSON data from the specified file, deserialises it, then writes it into an arrayList of
     * LngLat objects, storing the coordinates of each point and a duplicate of the first point at the end of the list
     * @param args the Url ending for the desired REST endpoint (not in use in CW1)
     */
    public static void main(String args) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            String baseUrl = "https://ilp-rest.azurewebsites.net/" ;

            //Read JSON data and deserialise it from centralArea
            CentralAreaPoint[] centralAreaPoints = mapper.readValue(
                    new URL( baseUrl + "centralArea") , CentralAreaPoint[].class ) ;

            //for each point in the centralArea array:
            //add an entry to centralArea in form of LngLat object with Lng and Lat of the current point
            for (CentralAreaPoint point : centralAreaPoints) {
                centralAreaXY.add(new LngLat(point.getLongitude(), point.getLatitude()));

            }
            //add duplicate of first point to the end so that it can be used to complete a polygon.
            centralAreaXY.add(new LngLat(centralAreaPoints[0].getLongitude(), centralAreaPoints[0].getLatitude()));



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return Gets the array of centralArea coordinates
     */
    public List<LngLat> getCentralAreaXY(){
        return centralAreaXY;
    }


    public void populateEdges(){
        for (int i=0; i<centralAreaXY.size()-1;i++){
            edges.add(new Line(centralAreaXY.get(i),centralAreaXY.get(i+1),null));
        }
    }



    /**
     *
     * @return returns the instance of CentralAreaClient
     */
    public CentralAreaClient getInstance(){
            return inst;
    }

}
