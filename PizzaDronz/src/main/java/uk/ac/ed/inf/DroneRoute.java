package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.Collections;

public class DroneRoute {

    public static ArrayList<Line> steps;
    public static int moves = 0;

    public DroneRoute(ArrayList<Line> inList){
        steps = inList;
    }

    public void incMoves(){
        moves++;
    }

    public ArrayList<Line> getSteps(){
        return steps;
    }

    public int getMoves(){
        return moves;
    }

    public void updateSteps(Line step){
        steps.add(step);
    }

    public void stepDump(ArrayList<Line> stepps){
        steps.addAll(stepps);
    }


}
