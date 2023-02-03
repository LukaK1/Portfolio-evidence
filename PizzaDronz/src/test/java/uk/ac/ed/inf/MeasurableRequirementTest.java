package uk.ac.ed.inf;

public class MeasurableRequirementTest {

    // The following test descriptions are for the requirement:
    // "The system must calculate the flightpaths for all the day's orders within 60 seconds."
    // In the actual project I could not complete the flightpath algorithm and system as a whole working piece
    // of software, so I am unable to actually implement working system tests, and instead I am describing how I
    // would have done them in this file.


    //================================= SYSTEM PERFORMANCE TESTS =======================================

    /**

    @Test
     public void flightpathCalculationTimeSystemTest(){

        The instrumentation for this test would be a timer to get the times between the start
        of the flightpath calculations and at the end and returning the time taken to complete this.

        Run command to initialise the system. (Read REST data, build no-fly-zones, restaurants etc)
        Validate the orders for the given day.

        Start flightpath calculations and timer
        *Calculations complete and timer stops

        Print(time taken to complete algorithm)
        assert time taken < 60 seconds.

    }

     The above test would be set up with varying number of valid orders in the dataset to test how fast the algorithm
     is for a single delivery all the way up to the point when it surpasses 60 seconds, and then it can be determined
     if that many orders sufficiently exceeds the upper limit of expected daily real-world orders such that we can
     verify the requirement has been met.


     */

    //--------------------------------------------------------------------------------------
}
