package uk.ac.ed.inf;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


public class Pathfinder {


    public static double[] bearings = {0, 22.5, 45, 67.5, 90, 112.5, 135, 157.5, 180, 202.5, 225, 247.5, 270, 292.5, 315, 337.5};

    public static LngLat appleton = new LngLat(-3.186874, 55.944494);

    public static LngLat drone = new LngLat(-3.186874, 55.944494);

    public static LngLat realDrone = new LngLat(-3.186874, 55.944494);


    public static DroneRoute droneRoute = new DroneRoute(new ArrayList<>());

    public static DroneRoute realDroneRoute = new DroneRoute(new ArrayList<>());


    //linear interpolation function
    private static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    /**
     * Given two line segments, calculates the coordinates of their point of intersect. Makes use of linear interpolation
     * @param A Line 1 point A
     * @param B Line 1 point B
     * @param C Line 2 point A
     * @param D Line 2 point B
     * @return A new LngLat object for resultant intersection or null if none
     */
    public static LngLat lineIntersect(LngLat A, LngLat B, LngLat C, LngLat D) {


        //if line follows a no-fly zone, go to correct vertex
        if ((A.equals(C) & getAngle(A, B) == getAngle(A, D))){
            return D;
        } else if (A.equals(D) & getAngle(A, B) == getAngle(A, C)){
            return C;
        }

         if (B.equals(C) | B.equals(D)){
            return B;
        }
        //The boring calculations-------------------------------------
        double tTop = (D.getLng() - C.getLng()) * (A.getLat() - C.getLat()) - (D.getLat() - C.getLat()) * (A.getLng() - C.getLng());
        double uTop = (C.getLat() - A.getLat()) * (A.getLng() - B.getLng()) - (C.getLng() - A.getLng()) * (A.getLat() - B.getLat());
        double bottom = (D.getLat() - C.getLat()) * (B.getLng() - A.getLng()) - (D.getLng() - C.getLng()) * (B.getLat() - A.getLat());


        if (bottom != 0) {
            double t = tTop / bottom;
            double u = uTop / bottom;

            if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {
                double x = lerp(A.getLng(), B.getLng(), t);
                double y = lerp(A.getLat(), B.getLat(), t);

                //check if the intersect is at the point that line 1 starts from in which case return null
                if(x == A.Lng() & y == A.Lat()){
                    return null;
                }
                //Segments intersect
                return new LngLat(x, y);
            } else {
                //Lines intersect outside segment constraints
                return null;
            }
        } else {
            //Lines are parallel so no intersection
            return null;
        }

    }


    /**
     * Loop through all edges of no-fly zone and record any intersections with the given line
     * @param A LngLat start point of line to check
     * @param B LngLat end point of line to check
     * @param poly The given No-Fly zone to check for intersects with
     * @return ArrayList of the Intersections detected
     */
    public static ArrayList<Intersection> polyIntersect(LngLat A, LngLat B, NoFlyZone poly) {

        //make record/class to store information about intersections so that you can return that and retain info

        List<LngLat> dupes = new ArrayList<>();

        int i;
        ArrayList<Intersection> intersects = new ArrayList<Intersection>();
        //loop through each edge of no fly zone
        for (i = 0; i < poly.edges.size(); i++) {
            //run lineIntersect on edge and Line
            LngLat intPoint = lineIntersect(A, B, poly.edges.get(i).A(), poly.edges.get(i).B());
            if (intPoint != null) {

                //catch duplicate intersects at corners
                if(intPoint.equals(poly.edges.get(i).B())){
                    dupes.add(intPoint);
                    intersects.add(new Intersection(intPoint, poly, i));
                }
                if(!dupes.contains(intPoint)) {
                    intersects.add(new Intersection(intPoint, poly, i));
                }
            }

        }
        return intersects;

    }

    //get the list of intersections of all polygons


    /**
     * Checks for the closest intersections of line and each no fly zone
     * @param A start point of Line
     * @param B end point of line
     * @return ArrayList of Intersection which contains the intersection of each no fly zone that is closest to A
     */
    public static ArrayList<Intersection> allPolyIntersects(LngLat A, LngLat B) {
        ArrayList<Intersection> intersects = new ArrayList<Intersection>();
        for (NoFlyZone zone : BuildNoFlyZones.noFlyZones) {

            Intersection toAdd = getClosest(polyIntersect(A, B, zone), A);
            if (toAdd != null) {
                intersects.add(toAdd);
            }
        }
        return intersects;
    }

    //get the closest intersection to the current location

    /**
     * Gets the closest intersection to currentPos in a list of Intersections
     * @param intersections ArrayList of intersections to be operated on
     * @param currentPos position to which you want the closest intersection
     * @return Closest intersection
     */
    public static Intersection getClosest(ArrayList<Intersection> intersections, LngLat currentPos) {
        if (intersections.isEmpty()){
            return null;
        }
        Intersection closest = intersections.get(0);
        for (int i = 1; i < intersections.size(); i++) {
            if (currentPos.distanceTo(intersections.get(i).point()) < currentPos.distanceTo(closest.point())) {
                closest = intersections.get(i);
            }
        }
        return closest;
    }


    /**
     * helper function to get difference between two numbers
     * @param a number one
     * @param b number two
     * @return the difference as a double
     */
    public static double difference(double a, double b) {
        double big;
        double small;

        if (a > b) {
            big = a;
            small = b;

        } else {
            big = b;
            small = a;
        }

        //if ((big - small) > 180)
        //    return ((big - small) - 180);

        return (big - small);

    }


    /**
     * gets the bearing from point A to point B
     * @param A origin point LngLat
     * @param B target point LngLat
     * @return the bearing from A to B as a double
     */
    public static double getAngle(LngLat A, LngLat B){

        double x1 = A.Lng();
        double x2 = B.Lng();
        double y1 = A.Lat();
        double y2 = B.Lat();

        double Y = (y2 - y1);
        double X = (x2 - x1);
        double raw = Math.atan2(Y,X)*(180/Math.PI);
        //adjust for negative values
        if (raw<0){
            double add = difference(raw, -180);
            return Math.abs(raw)+(2*add);
        }
        return raw;
    }

    /**
     * Test if a line from A through vertex and beyond intersects only once (at that vertex) with that line's NoFlyZone.
     * This decides if this vertex is a suitable step towards a path around the No-Fly zone
     * the Move distance of 0.25 is chosen to be reasonable long to be likely it extends beyond the limits of the No fly zone
     * @param A Point from which to draw line
     * @param bearing the bearing in which to draw the line
     * @param edge the no-fly zone edge that we are dealing with
     * @param poly the No-fly zone with which we are concerned
     * @return True if the vertex is a viable next step, otherwise false
     */
    public static boolean testLine(LngLat A, double bearing, Line edge, NoFlyZone poly) {

        LngLat intersect = lineIntersect(A, edge.B(), edge.A(), edge.B());

        if (polyIntersect(A, edge.B(),poly).size() == 1){

            if (intersect.equals(edge.B())) {

                Line pathLine = new Line(A, edge.B().nextPosition(bearing, 0.25), null);
                System.out.println(pathLine);

                return polyIntersect(pathLine.A(), pathLine.B(), poly).size() == 0;
            }

        } System.out.println("more than 1 intersect on poly");

        return false;

    }

    /**
     * Recursive algorithm to find the straight line paths from A to destination via the vertices of obstructing No-Fly zones.
     * Recursively forks to navigate around any obstruction, adding the current step to the list of those returned by the recursion
     * @param A Starting point
     * @param intersect the closest intersection between A and the destination
     * @param poly The No-Fly zone of the intersection given
     * @param destination the final stop of the path
     * @return ArrayList of the Paths found by the algorithm from A to destination
     */
    public static ArrayList<Path> getFork(LngLat A, Intersection intersect, NoFlyZone poly, LngLat destination) {

        ArrayList<Path> forks = new ArrayList<>();

        //If there is no obstruction between current pos and destination, add A-Destination path to forks and return
        System.out.println("Intersection = " + intersect);
        ArrayList<LngLat> visited = new ArrayList<>();
        int i = 0;
        for (Line edge : intersect.poly().edges) {




            if (!visited.contains(edge.B()) & testLine(edge.B(), getAngle(A, edge.B()), edge, poly)) {
                visited.add(edge.B());
                System.out.println("testline passed");
                //recurse on current no-fly zone
                System.out.println("No zone in the way");
                System.out.println("no obstruction to point " + edge.B());
                System.out.println("the next point we are aiming for " + destination);

                System.out.println("INTERSECT" + allPolyIntersects(edge.B(), destination));

                if (!(allPolyIntersects(edge.B(), destination)).isEmpty()) {


                    if (getClosest(allPolyIntersects(edge.B(), destination), edge.B()).poly() == intersect.poly()) {

                        Line nextEdge = intersect.poly().edges.get(i+1);
                        ArrayList<Path> X = getFork(destination, getClosest(allPolyIntersects(nextEdge.B(), destination), nextEdge.B()), (getClosest(allPolyIntersects(nextEdge.B(), destination), edge.B())).poly(), nextEdge.B());
                        for (Path path : X) {
                            Path pathToAdd = new Path(path.steps());
                            pathToAdd.steps().add(new Line(nextEdge.B(), destination, intersect.poly()));
                            pathToAdd.steps().add(new Line(A, edge.B(), intersect.poly()));
                            forks.add(pathToAdd);
                        }
                    } else {

                        ArrayList<Path> X = getFork(destination, getClosest(allPolyIntersects(edge.B(), destination), edge.B()), (getClosest(allPolyIntersects(edge.B(), destination), edge.B())).poly(), edge.B());
                        for (Path path : X) {
                            Path pathToAdd = new Path(path.steps());
                            pathToAdd.steps().add(new Line(edge.B(), A, intersect.poly()));
                            forks.add(pathToAdd);



                        }
                    }
                    } else {
                        ArrayList<Line> end = new ArrayList<>();
                        end.add(new Line(A, edge.B(), intersect.poly()));

                        Path pathToAdd = new Path(end);
                        pathToAdd.steps().add(new Line(edge.B(), destination, intersect.poly()));
                        forks.add(pathToAdd);
                        System.out.println("ADDED FINAL ONE " + pathToAdd);
                        System.out.println("FORKS" + forks);
                        System.out.println("= + + =+ + == = + = +========================     0000000000      ==== == = ======= ============== =");


                        return forks;
                    }
                }
                i++;
            }


            return forks;

    }


    /**
     * Checks the length of each Line in a given Path and sums them
     * @param path single Path
     * @return total length of path as a double
     */
    public static double pathDistance(Path path) {
        double sum = 0;

        for (int i = 0; i < path.steps().size(); i++) {
            sum = sum + path.steps().get(i).getLength();
        }
        return sum;

    }


    /**
     * gets length of each path in ArrayList and finds the shortest one
     * @param paths ArrayList of Paths
     * @return the shortest Path
     */
    public static Path getShortest(ArrayList<Path> paths) {


        Path shortest = paths.get(0);
        for (int i = 1; i < paths.size() - 1; i++) {
            if (pathDistance(paths.get(i)) < pathDistance(shortest)) {
                shortest = paths.get(i);
            }
        }
        return shortest;

    }


    /**
     *Draws a line out from point in bearing 180 and checks if the number of intersections with the No-Fly zone is even or odd
     * if even then point is outside zone, otherwise it is inside
     * @param point point to check if in zone
     * @param poly zone to check if point is in
     * @return true if point not in zone, otherwise false
     */
    public static boolean notInsideZone(LngLat point, NoFlyZone poly) {

        Line checkLine = new Line(point, point.nextPosition(180, 0.7), null);
        return polyIntersect(checkLine.A(), checkLine.B(), poly).size() % 2 == 0;

    }


    /**
     * finds the closest of the 16 available compass directions to the target bearing
     * loops through 16 bearings, if moving in that bearing doesnt move drone through or into a zone,
     * get difference between bearing and shortest bearing and update shortest = bearing if appropriate
     * @param target bearing to end point of line
     * @param path the path the drone is delivering on
     * @param lineNumber the index of the path line the drone is currently on
     * @return the closest of the 16 bearings to the target bearing
     */
    public static double findClosest(double target, Path path, int lineNumber) {

        int prevLine = lineNumber - 1;
        if (lineNumber == 0){
            prevLine = lineNumber;
        }
        boolean first = true;
        double closest = 0;
        for (double bearing : bearings) {
              Line test = new Line(drone, drone.nextPosition(bearing, 0.00015), null);


            if ( notInsideZone(test.B(), path.steps().get(lineNumber).toPoly()) && notInsideZone(test.B(), path.steps().get((prevLine) % path.steps().size()).toPoly())){


                if (allPolyIntersects(test.A(), test.B()).isEmpty()) {
                    //          notInsideZone(drone.nextPosition(bearing, 0.00015), path.steps().get((prevLine) % path.steps().size()).toPoly())) {

                    if (first) {
                        closest = bearing;
                        first = false;
                    }

                    if (difference(bearing, target) < difference(closest, target)) {
                        closest = bearing;
                    }


                }
            }
        }


        System.out.println(target);
        System.out.println(closest);
            return closest;
        }



    /**
     * function to take a path and re-order the lines within to start with the previous line's end point
     * @param path
     * @return re-ordered path
     */
    public static Path orderPath(Path path){



        for (int i = 1; i < path.steps().size(); i++){

            if (path.steps().get(i).B().equals(path.steps().get(i-1).B())){
                path.steps().set(i, new Line(path.steps().get(i).B(),path.steps().get(i).A(), path.steps().get(i).toPoly()));
            }

        }

            return path;
        }

    /**
     * function to call getClosest(getFork()) between A and B
     * @param A Start point
     * @param B End pointa
     */
    public static Path getPath(LngLat A, LngLat B) {
        Intersection ABintersect = getClosest(allPolyIntersects(A, B), A);
        if (ABintersect == null) {
            ArrayList<Line> line = new ArrayList<>();
            line.add(new Line(B, A, null));
            return orderPath(new Path(line));

        } else {

            Path rawPathToPoint = getShortest(getFork(B, ABintersect, ABintersect.poly(), A));

            return orderPath(rawPathToPoint);
        }
    }

    /**
     * Move the drone from one end of the path to the other
     * repeat move the drone in the best direction for following line while not crossing any zones
     * until end reached or drone has made 2000 moves.
      * @param pathToFollow the optimal delivery path for drone to follow
     * @param destination
     */
    public static void moveToPoint(Path pathToFollow, LngLat destination){


        for (int i=0;i<pathToFollow.steps().size(); i++) {


            if (droneRoute.getMoves() == 2000) {
                break;
            }



            int x = 0;
            while (!drone.closeTo(pathToFollow.steps().get(i).B())) {
                if (droneRoute.getMoves() == 2000) {
                    break;
                }

                double trueAngle = getAngle(drone, pathToFollow.steps().get(i).B());
                double bearing = findClosest(trueAngle, pathToFollow, i);



                LngLat next = drone.nextPosition(bearing, 0.00015);
                droneRoute.updateSteps(new Line(drone, next, null));
                drone = next;
                droneRoute.incMoves();
                x++;


            }
            double angle = Pathfinder.getAngle(drone, pathToFollow.steps().get(1).B());

            System.out.println(pathToFollow);
            System.out.println(angle);
            System.out.println(findClosest(angle, pathToFollow, 1));


        }

    }


    /**
     * controls the drone for each delivery, finding the path to take, ordering it and then flying the drone
     * there and back.
     * @param drone start position of the drone
     * @param route drone route for this delivery
     * @param destination the restaurant to go to
     */
    public static void droneRoute(LngLat drone, DroneRoute route, LngLat destination){

        //Get to restaurant

        Path pathToRestaurant = getPath(drone, destination);

        moveToPoint(pathToRestaurant, drone);

        System.out.println("PATH TO RESTAURANT COMPLETE_________________________");

        //return to appleton
        Path pathToAppleton = getPath(appleton, drone);

        moveToPoint(pathToAppleton, appleton);
        }




    }

