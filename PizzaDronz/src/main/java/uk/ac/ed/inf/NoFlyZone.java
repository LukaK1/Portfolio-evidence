package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class NoFlyZone {

    @JsonProperty ("name")
    String name;

    @JsonProperty ("coordinates")
    double[][] rawCoordinates;

    List<LngLat> coords = new ArrayList<LngLat>();

    List<Line> edges = new ArrayList<Line>();








}
