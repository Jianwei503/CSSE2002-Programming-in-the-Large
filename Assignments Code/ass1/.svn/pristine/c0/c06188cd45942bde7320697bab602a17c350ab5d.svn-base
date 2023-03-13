package passengers;

import org.junit.Before;
import org.junit.Test;
import stops.Stop;

import static org.junit.Assert.*;

public class PassengerTest {
    Passenger paxWithName;
    Passenger paxWithNameDest;

    @Before
    public void setUp() throws Exception {
        //generate a Passenger instance only with name.
        paxWithName = new Passenger("Abel\nSmith\r");

        //generate a Stop class named "stop" as the destination
        Stop stop = new Stop("UQ Lakes",23,56);
        //generate a Passenger instance with name and destination
        paxWithNameDest = new Passenger("\rAbel\nSmith",stop);
    }

    @Test
    public void getName() {
        assertEquals("AbelSmith", paxWithName.getName());
        assertEquals("AbelSmith", paxWithNameDest.getName());
    }

    @Test
    public void getNullValuedName() {
        paxWithName = new Passenger(null);
        assertEquals("", paxWithName.getName());
    }

    @Test
    public void getDestination() {

        String destination = paxWithNameDest.getDestination().toString();

        assertTrue(destination.equals( "{UQ Lakes} : {23} : {56}" ) );
    }

    @Test
    public void getNoDestination() {
        assertEquals(null,paxWithName.getDestination());
    }

    @Test
    public void setDestination() {
        Stop destination = new Stop("Acacia Ridge",-27,156);
        paxWithNameDest.setDestination(destination);

        String setDestination = paxWithNameDest.getDestination().toString();
        assertTrue(setDestination.equals( "{Acacia Ridge} : {-27} : {156}" ) );
    }

    @Test
    public void setNullValuedDest() {
        paxWithNameDest.setDestination(null);
        assertEquals(null,paxWithNameDest.getDestination() );
    }

    @Test
    public void toString1() {
        assertEquals("Passenger named AbelSmith",paxWithName.toString());
    }

    @Test
    public void toStringWithEmptyName() {
        Passenger paxWithEmptyName = new Passenger("");
        assertEquals("Anonymous passenger",paxWithEmptyName.toString());
    }
}