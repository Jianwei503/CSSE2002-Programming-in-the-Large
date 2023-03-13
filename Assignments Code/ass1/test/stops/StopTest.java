package stops;

import exceptions.NoNameException;
import exceptions.OverCapacityException;
import org.junit.Before;
import org.junit.Test;
import passengers.Passenger;
import routes.BusRoute;
import routes.Route;
import vehicles.Bus;
import vehicles.PublicTransport;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StopTest {

    private Stop stop;

    @Before
    public void setUp() throws Exception {
        stop = new Stop("Acacia\nRidge\r",-27,153 );
    }

    @Test
    public void getName() {
        //test for removing any newline characters ('\n') or carriage returns ('\r')
        assertEquals("AcaciaRidge",stop.getName() );
    }

    @Test ( expected = NoNameException.class )
    public void getNullValuedName() {
        //test for null valued name, should throw NoNameException
        stop = new Stop(null,-27,153 );
        stop.getName();
    }

    @Test ( expected = NoNameException.class )
    public void getEmptyValuedName() {
        //test for empty valued name, should throw NoNameException
        stop = new Stop("",-27,153 );
        stop.getName();
    }

    @Test
    public void getX() {
        assertEquals(-27,stop.getX() );
    }

    @Test
    public void getY() {
        assertEquals(153,stop.getY() );
    }

    @Test
    public void addRoute() {
        Route busRoute = new BusRoute( "AcaciaRidge-Woolloongabba",115);
        busRoute.addStop( new Stop("Calamvale",-30,160 ) );

        //verify that the busRoute has not yet been added to stop
        assertEquals(0, stop.getRoutes().size() );

        stop.addRoute(busRoute);

        //verify that the busRoute has already been added to stop
        assertEquals(1, stop.getRoutes().size() );
    }

    @Test
    public void addNullValuedRoute() {
        Route busRoute = null;
        stop.addRoute(busRoute);
        //verify that the busRoute with null valued name has not yet been added to stop
        assertEquals(0, stop.getRoutes().size());
    }

    @Test
    public void getRoutes() {
        Route busRoute_1 = new BusRoute( "AcaciaRidge-Woolloongabba",115);
        Route busRoute_2 = new BusRoute( "Inala-City",110);

        stop.addRoute(busRoute_1);
        stop.addRoute(busRoute_2);

        List routes = stop.getRoutes();
        routes.clear();

        assertEquals(0,routes.size() );
        //the routes in returned list have been cleared
        assertEquals(2,stop.getRoutes().size() );
        //internal state of the routes remain unchanged
    }

    @Test
    public void addNeighbouringStop() {
        Stop neighbourStop = new Stop("Archerfield",-27,154 );
        Stop neighbourStop_1 = new Stop("Inala",-20,144 );

        stop.addNeighbouringStop(neighbourStop);
        assertEquals( 1,stop.getNeighbours().size() );
        stop.addNeighbouringStop(neighbourStop_1);
        assertEquals( 2,stop.getNeighbours().size() );
    }

    @Test
    public void addExistedNeighbourStop() {
        Stop neighbourStop = new Stop("Archerfield",-27,154 );
        stop.addNeighbouringStop(neighbourStop);
        assertEquals( 1,stop.getNeighbours().size() );

        stop.addNeighbouringStop(neighbourStop);
        //neighbourStop is existed, therefor won't be recorded
        assertEquals( 1,stop.getNeighbours().size() );
    }

    @Test
    public void addNullValuedStop() {
        assertEquals( 0,stop.getNeighbours().size() );

        stop.addNeighbouringStop(null);
        assertEquals( 0,stop.getNeighbours().size() );
    }

    @Test
    public void getNeighbours() {
        Stop stop_1 = new Stop( "Archerfield",-27,154);
        Stop stop_2 = new Stop( "Inala",-25,153);
        Stop stop_3 = new Stop( "Sunnybank",-15,130);

        stop.addNeighbouringStop(stop_1);
        stop.addNeighbouringStop(stop_2);
        stop.addNeighbouringStop(stop_3);
        assertEquals(3,stop.getNeighbours().size() );

        List neighbours = stop.getNeighbours();
        neighbours.clear();
        assertEquals(0,neighbours.size() );
        //the stops in returned list have been cleared
        assertEquals(3,stop.getNeighbours().size() );
        //internal state of the neighbouring stops remain unchanged
    }

    @Test
    public void addPassenger() {
        Stop stop = new Stop("Archerfield",-27,154 );
        Passenger passenger = new Passenger("Abel\nSmith",stop);
        assertEquals( 0,stop.getWaitingPassengers().size() );

        stop.addPassenger(passenger);
        assertEquals( 1,stop.getWaitingPassengers().size() );
    }

    @Test
    public void addNullValuedPassenger() {
        assertEquals( 0,stop.getWaitingPassengers().size() );

        stop.addPassenger(null);
        assertEquals( 0,stop.getWaitingPassengers().size() );
    }

    @Test
    public void getWaitingPassengers() {
        Passenger passenger_1 = new Passenger("Abel Smith");
        Passenger passenger_2 = new Passenger("Don Nicklin");
        stop.addPassenger(passenger_1);
        stop.addPassenger(passenger_2);
        List waitingPassengers = stop.getWaitingPassengers();
        waitingPassengers.clear();
        assertEquals(0,waitingPassengers.size() );
        //the passengers in returned list have been cleared
        assertEquals(2,stop.getWaitingPassengers().size() );
        //internal state of the passengers' list remain unchanged
    }

    @Test (timeout = 1000)
    public void getWaitPassengersOder() {
        //verify that if the order of the passengers in the returned list is
        // the same as the order in which the passengers were added to the stop.
        Passenger passenger_1 = new Passenger("Abel Smith");
        Passenger passenger_2 = new Passenger("Don Nicklin");
        stop.addPassenger(passenger_1);
        stop.addPassenger(passenger_2);
        List waitingPassengers = stop.getWaitingPassengers();

        List expectedNameOder = new ArrayList();
        List actualNameOder = new ArrayList();
        expectedNameOder.add("Abel Smith");
        expectedNameOder.add("Don Nicklin");

        for (Object p : waitingPassengers) {
            Passenger passenger = (Passenger) p;
            actualNameOder.add( passenger.getName() );
        }
        assertTrue( expectedNameOder.equals( expectedNameOder ) );
    }

    @Test
    public void isAtStop() {
        Route busRoute = new BusRoute( "Inala-City",110);
        busRoute.addStop( new Stop("Calamvale",-30,160 ) );

        PublicTransport transport = new Bus(1,50,
                busRoute,"078TZL" );

        //not at this stop, should return false
        assertFalse( stop.isAtStop(transport) );

        stop.transportArrive(transport);
        assertTrue( stop.isAtStop(transport) );
    }

    @Test
    public void getVehicles() {
        Route busRoute_1 = new BusRoute( "Inala-City",110);
        busRoute_1.addStop( new Stop("Calamvale",-30,160 ) );

        Route busRoute_2 = new BusRoute( "Sunnybank-City",135);
        busRoute_2.addStop( new Stop("Sunnybank",-20,150 ) );

        PublicTransport transport_1 = new Bus(1,50,
                busRoute_1, "078TZL" );
        PublicTransport transport_2 = new Bus(2,50,
                busRoute_2, "918RFX" );

        stop.transportArrive(transport_1);
        stop.transportArrive(transport_2);

        List vehicles = stop.getVehicles();
        vehicles.clear();

        assertEquals(0,vehicles.size() );
        //the vehicles in returned list have been cleared
        assertEquals(2,stop.getVehicles().size() );
        //internal state of the vehicles currently at this stop remain unchanged
    }

    @Test
    public void transportArrive() {
        Route busRoute = new BusRoute( "Inala-City",110);
        busRoute.addStop( new Stop("Calamvale",-30,160 ) );

        PublicTransport transport = new Bus(1,50,
                busRoute, "078TZL" );

        try {
            transport.addPassenger(new Passenger("Abel Smith"));
            transport.addPassenger(new Passenger("Don Nicklin"));
        } catch (OverCapacityException e) {
            e.printStackTrace();
        }

        //before calling the method of transportArrive()
        //there should be 0 passenger waiting at this stop
        assertEquals(0,stop.getWaitingPassengers().size() );

        stop.transportArrive( transport );

        //before calling the method of transportArrive()
        //there should be 0 passenger waiting at this stop
        assertEquals(2,stop.getWaitingPassengers().size() );

        // recording the vehicle itself at this stop
        assertTrue(stop.getVehicles().get(0).getId() == 1 );

        //should not update the location of the transport
        assertTrue(transport.getCurrentStop().getName() == "Calamvale" );
    }

    @Test
    public void transportArriveExistedStop() {
        Route busRoute = new BusRoute( "Inala-City",110);
        busRoute.addStop( new Stop("Calamvale",-30,160 ) );

        PublicTransport transport = new Bus(1,50,
                busRoute, "078TZL" );

        try {
            transport.addPassenger(new Passenger("Abel Smith"));
            transport.addPassenger(new Passenger("Don Nicklin"));
        } catch (OverCapacityException e) {
            e.printStackTrace();
        }

        stop.transportArrive( transport );
        //after calling the method of transportArrive()
        //there should be 2 passenger waiting at this stop
        assertEquals(2,stop.getWaitingPassengers().size() );
        assertTrue(stop.getVehicles().get(0).getId() == 1 );

        stop.transportArrive( transport );//recall this method with same transport
        assertEquals(2,stop.getWaitingPassengers().size() );
        assertTrue(stop.getVehicles().get(0).getId() == 1 );
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void transportArriveWithNull() {
        stop.transportArrive( null );//call this method with null value
        assertEquals(0,stop.getWaitingPassengers().size() );
        stop.getVehicles().get(0);
    }

    @Test
    public void transportDepart() {
        //create a busRoute contains the stop and the nextStop
        Route busRoute = new BusRoute( "Inala-City",110);
        Stop nextStop = new Stop("Calamvale",-30,160 );
        busRoute.addStop( stop );
        busRoute.addStop( nextStop );

        PublicTransport transport = new Bus(1,50,
                busRoute, "078TZL" );

        stop.transportArrive( transport );
        stop.transportDepart( transport,nextStop );

        assertEquals( 0,stop.getVehicles().size() );
        assertTrue(nextStop.getVehicles().get(0).getId() == 1 );
        assertTrue (transport.getCurrentStop().getName() == "Calamvale");
    }

    @Test
    public void transportDepartVehicleNotHere() {
        //create a busRoute contains the stop and the nextStop
        Route busRoute = new BusRoute( "Inala-City",110);
        Stop nextStop = new Stop("Calamvale",-30,160 );
        busRoute.addStop( stop );
        busRoute.addStop( nextStop );

        PublicTransport transport = new Bus(1,50,
                busRoute, "078TZL" );

        stop.transportDepart( transport,new Stop("Woolloongabba",-10,176 ) );

        assertEquals( 0,stop.getVehicles().size() );
        assertEquals( 0,nextStop.getVehicles().size() );
        System.out.println(transport.getCurrentStop().getName());
        assertTrue (transport.getCurrentStop().getName().equals("AcaciaRidge") );
    }

    @Test
    public void transportDepartNullVehicle() {
        Stop nextStop = new Stop("Calamvale",-30,160 );
        stop.transportDepart( null,nextStop );

        assertEquals( 0,stop.getVehicles().size() );
        assertEquals( 0,nextStop.getVehicles().size() );
    }

    @Test
    public void transportDepartNullStop() {
        //create a busRoute contains the stop and the nextStop
        Route busRoute = new BusRoute( "Inala-City",110);
        Stop nextStop = null;
        busRoute.addStop( stop );
        busRoute.addStop( nextStop );

        PublicTransport transport = new Bus(1,50,
                busRoute, "078TZL" );

        stop.transportArrive( transport );
        stop.transportDepart( transport,nextStop );

        assertEquals( 1,stop.getVehicles().size() );
        assertTrue (transport.getCurrentStop().getName().equals("AcaciaRidge"));
    }

    @Test
    public void distanceTo() {
        Stop nextStop = new Stop("Calamvale",-30,160 );
        assertEquals(10,stop.distanceTo(nextStop) );
    }

    @Test
    public void distanceToNullValuedStop() {
        assertEquals(-1,stop.distanceTo(null) );
    }

    @Test
    public void hashCode1() {
        Stop stopCopy = new Stop("Acacia\nRidge\r",-27,153 );
        Route busRoute = new BusRoute( "Inala-City",110);

        stop.addRoute(busRoute);
        stopCopy.addRoute(busRoute);
        stopCopy.addRoute(busRoute);

        assertTrue(stop.hashCode() == stopCopy.hashCode() );
    }

    @Test
    public void equals1() {
        Stop stopCopy = new Stop("Acacia\nRidge\r",-27,153 );

        Route busRoute = new BusRoute( "Inala-City",110);
        stop.addRoute(busRoute);
        stopCopy.addRoute(busRoute);
        stopCopy.addRoute(busRoute);

        assertTrue(stop.equals(stopCopy));
    }

    @Test
    public void equalsIsFalse() {
        Stop stopCopy = new Stop("Acacia\nRidge\r",-20,153 );
        Route busRoute = new BusRoute( "Inala-City",110);

        stop.addRoute(busRoute);
        stopCopy.addRoute(busRoute);

        assertFalse(stop.equals(stopCopy));
    }

    @Test
    public void equalsToItself() {
        assertTrue(stop.equals(stop));
    }

    @Test
    public void equalsToNullIsFalse() {
        assertFalse(stop.equals(null));
    }

    @Test
    public void toString1() {
        assertTrue(stop.toString().equals( "{AcaciaRidge} : {-27} : {153}" ) );
    }

}