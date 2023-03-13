package vehicles;

import exceptions.OverCapacityException;
import org.junit.Before;
import org.junit.Test;
import passengers.Passenger;
import routes.BusRoute;
import routes.Route;
import stops.Stop;

import java.util.List;

import static org.junit.Assert.*;

public class PublicTransportTest {
    PublicTransport bus;
    Route busRoute = new BusRoute( "AcaciaRidge-Woolloongabba",110);


    @Before
    public void setUp() throws Exception {
        busRoute.addStop( new Stop("Acacia Ridge",-27,153 ) );
        bus = new Bus(1,10,busRoute,"078TZL");
    }

    @Test
    public void getRoute() {
        assertTrue( bus.getRoute().equals(busRoute) );
    }

    @Test
    public void getId() {
        assertEquals(1,bus.getId());
    }

    @Test
    public void getCurrentStop() {
        assertTrue(bus.getCurrentStop().getName().equals("Acacia Ridge"));
    }

    @Test
    public void getCurrentStopWithEmptyRoute() {
        Route busRoute1 = new BusRoute( "AcaciaRidge-Woolloongabba",110);
        PublicTransport bus1 = new Bus(1,10,busRoute1,"078TZL");
        assertEquals(null,bus1.getCurrentStop() );
    }

    @Test
    public void passengerCount() {
        try {
            bus.addPassenger(new Passenger( "Abel Smith" ) );
            bus.addPassenger(new Passenger( "Don Nicklin" ) );
        } catch (OverCapacityException e) {
            e.printStackTrace();
        }
        assertEquals(2,bus.passengerCount() );
    }

    @Test
    public void passengerCountInitially() {
        assertEquals(0,bus.passengerCount() );
    }

    @Test
    public void getCapacity() {
        assertEquals(10,bus.getCapacity() );
    }

    @Test
    public void getType() {
        assertEquals("bus",bus.getType() );
    }

    @Test
    public void getPassengers() {
        try {
            bus.addPassenger(new Passenger( "Abel Smith" ) );
            bus.addPassenger(new Passenger( "Don Nicklin" ) );
        } catch (OverCapacityException e) {
            e.printStackTrace();
        }
        assertEquals(2,bus.getPassengers().size() );
    }

    @Test
    public void getPassengersInitially() {
        assertEquals(0,bus.getPassengers().size() );
    }

    @Test
    public void getPassengersModifying() {
        try {
            bus.addPassenger(new Passenger( "Abel Smith" ) );
            bus.addPassenger(new Passenger( "Don Nicklin" ) );
        } catch (OverCapacityException e) {
            e.printStackTrace();
        }
        List<Passenger> passengers = bus.getPassengers();
        passengers.clear();
        assertEquals( 0,passengers.size() );
        assertEquals( 2,bus.getPassengers().size() );
    }

    @Test
    public void addPassenger() {
        try {
            bus.addPassenger(new Passenger( "Abel Smith" ) );
            bus.addPassenger(new Passenger( "Don Nicklin" ) );
        } catch (OverCapacityException e) {
            e.printStackTrace();
        }
        assertEquals(2,bus.passengerCount());
    }

    @Test
    public void addPassengerWithNullValue() {
        try {
            bus.addPassenger( null );
            bus.addPassenger( null );
        } catch (OverCapacityException e) {
            e.printStackTrace();
        }
        assertEquals(0,bus.passengerCount());
    }

    @Test ( expected = OverCapacityException.class )
    public void addPassengerOverCapacity() throws OverCapacityException {
        for ( int i = 0; i < 11; i++ ) {
            bus.addPassenger( new Passenger( "Abel Smith" ) );
        }
//        for ( int i = 0; i < 11; i++ ) {
//            try {
//                bus.addPassenger( new Passenger( "Abel Smith" ) );
//            } catch (OverCapacityException e) {
//                e.printStackTrace();
//            }
//        }
        assertEquals(10,bus.passengerCount());
    }

    @Test
    public void removePassenger() {
        Passenger passenger1 = new Passenger("Abel Smith" );
        Passenger passenger2 = new Passenger("Don Nicklin" );
        try {
            bus.addPassenger( passenger1 );
            bus.addPassenger( passenger2 );
        } catch (OverCapacityException e) {
            e.printStackTrace();
        }
        assertTrue( bus.removePassenger( passenger1 ) );
        assertEquals( 1, bus.passengerCount() );
        assertEquals("Don Nicklin",bus.getPassengers().get(0).getName());
    }

    @Test
    public void removePassengerNotOnBoard() {
        Passenger passenger1 = new Passenger("Abel Smith" );
        Passenger passenger2 = new Passenger("Don Nicklin" );
        try {
            bus.addPassenger( passenger1 );
            bus.addPassenger( passenger2 );
        } catch (OverCapacityException e) {
            e.printStackTrace();
        }
        assertFalse(bus.removePassenger(new Passenger("Abel Smith" )));
        assertEquals(2,bus.passengerCount());
    }

    @Test
    public void removePassengerPaxIsNull() {
        Passenger passenger1 = new Passenger("Abel Smith" );
        Passenger passenger2 = new Passenger("Don Nicklin" );
        try {
            bus.addPassenger( passenger1 );
            bus.addPassenger( passenger2 );
        } catch (OverCapacityException e) {
            e.printStackTrace();
        }
        assertFalse(bus.removePassenger(null));
        assertEquals(2,bus.passengerCount());
    }

    @Test
    public void unload() {
        try {
            bus.addPassenger( new Passenger( "Abel Smith" ) );
            bus.addPassenger( new Passenger( "Don Nicklin" ) );
        } catch (OverCapacityException e) {
            e.printStackTrace();
        }
        List<Passenger> unloadedPassengers = bus.unload();
        assertEquals(2,unloadedPassengers.size());
        assertTrue(bus.getPassengers().isEmpty());

        unloadedPassengers.add( new Passenger( "Abel Smith" ) );
        assertEquals( 0,bus.passengerCount() );
    }

    @Test
    public void unloadNoPassengerOnBoard() {
        List<Passenger> unloadedPassengers = bus.unload();
        assertTrue(unloadedPassengers.isEmpty());
    }

    @Test
    public void travelTo() {
        Stop neighbourStop = new Stop("Archerfield",-27,154 );
        busRoute.addStop(neighbourStop);

        assertEquals("Acacia Ridge",bus.getCurrentStop().getName());

        bus.travelTo(neighbourStop);
        assertEquals("Archerfield",bus.getCurrentStop().getName());
    }

    @Test
    public void travelToNullStop() {
        Stop neighbourStop = new Stop("Archerfield",-27,154 );
        busRoute.addStop(neighbourStop);

        assertEquals("Acacia Ridge",bus.getCurrentStop().getName());

        bus.travelTo(null);
        assertEquals("Acacia Ridge",bus.getCurrentStop().getName());
    }

    @Test
    public void travelToStopNotOnRoute() {
        Stop neighbourStop = new Stop("Archerfield",-27,154 );
        busRoute.addStop(neighbourStop);

        assertEquals("Acacia Ridge",bus.getCurrentStop().getName());

        bus.travelTo(new Stop("Archerfield",-27,154 ));
        assertEquals("Acacia Ridge",bus.getCurrentStop().getName());
    }

    @Test
    public void toString1() {
        assertEquals("bus number 1 (10) on route 110",bus.toString());
    }
}
