package uk.ac.ed.inf;

public record Line(LngLat A, LngLat B, NoFlyZone toPoly) {

    public double getLength() {
        return A.distanceTo(B);
    }
}
