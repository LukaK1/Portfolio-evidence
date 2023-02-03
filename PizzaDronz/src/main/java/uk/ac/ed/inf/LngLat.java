package uk.ac.ed.inf;

import java.util.List;

public record LngLat(double Lng, double Lat) {


    /**
     * Check if this point's longitude and latitude are between the smallest and largest lng and lats of the central area,
     * this checks whether the point is within the central area.
     * @return true if point is within central area and false if not.
     */
    // !!This method assumes a rectangle - a more suitable algorithm should be implemented later!!

    public boolean inCentralArea() {
            //instantiate the CentralAreaClient
            CentralAreaClient central = new CentralAreaClient();


              //create list of centralArea coordinates
              List<LngLat> centralCoords = central.getCentralAreaXY();

                //check this point longitude between the values of points 0 and 3's
        return (this.Lng >= centralCoords.get(0).Lng && this.Lng <= centralCoords.get(3).Lng) &&
                (this.Lat >= centralCoords.get(0).Lat && this.Lat <= centralCoords.get(1).Lat);
                //^^^check this point latitude between the values of points 0 and 1's  ^^^


    }

    /**
     * Calculates the distance between the current point and the point that is passed in
     * @param LngLatDist is another object of LngLat Record representing another point on the map
     * @return returns the distance between the points as a double.
     */
    public double distanceTo(LngLat LngLatDist){
        double lng  = (Lng - LngLatDist.Lng);
        lng = lng*lng;
        double lat = (Lat - LngLatDist.Lat);
        lat = lat*lat;

        double distanceSquared = lng+lat;
        return Math.sqrt(distanceSquared);
    }

    /**
     * Checks if this point is within 0.00015 degrees of the point that is passed in. Done using distanceTo method.
     * @param LngLatClose another object of the LngLat Record representing another point on the map
     * @return true if the points are within 0.00015 degrees of eachother and false otherwise
     */
    public boolean closeTo(LngLat LngLatClose){
        return distanceTo(LngLatClose) < 0.00015;
    }

    /**
     * Calculates the coordinates
     * @param bearing a compass bearing in degrees between 0 and 360
     * @return new position after a move of 0.00015 degrees in the direction of bearing
     */
    public LngLat nextPosition(double bearing, double move){
        //calculate new latitude
        double newLng = this.Lng + (move * Math.cos(Math.toRadians(bearing)));
        //calculate new longitude
        double newLat = this.Lat + (move * Math.sin(Math.toRadians(bearing)));
        return new LngLat(newLng, newLat);


    }


    public double getLat(){
        return Lat;
    }

    public double getLng(){
        return Lng;
    }




}
