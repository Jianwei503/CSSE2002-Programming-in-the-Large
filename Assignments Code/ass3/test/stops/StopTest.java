package stops;

import org.junit.Before;
import org.junit.Test;
import passengers.Passenger;
import routes.BusRoute;
import routes.Route;
import vehicles.Bus;
import vehicles.PublicTransport;

import static org.junit.Assert.*;

public class StopTest {
    Stop stop0, stop1, stop2, stop3, stop4, stop5, stop6;
    Passenger p1, p2, p3, p4;
    PublicTransport bus;
    Route busRoute;

    @Before
    public void setUp() throws Exception {
        stop0 = new Stop("stop0", 0, 0);
        stop1 = new Stop("stop1", -1, 0);
        stop2 = new Stop("stop2", 2, 0);
        stop3 = new Stop("stop3", 3, 0);
        stop4 = new Stop("stop4", 0, 1);
        stop5 = new Stop("stop5", 0, -1);
        stop6 = new Stop("stop6", 0, -2);

        stop0.addNeighbouringStop(stop1);
        stop1.addNeighbouringStop(stop0);

        stop0.addNeighbouringStop(stop2);
        stop2.addNeighbouringStop(stop0);

        stop2.addNeighbouringStop(stop3);
        stop3.addNeighbouringStop(stop2);

        stop0.addNeighbouringStop(stop4);
        stop4.addNeighbouringStop(stop0);

        stop0.addNeighbouringStop(stop5);
        stop5.addNeighbouringStop(stop0);

        stop5.addNeighbouringStop(stop6);
        stop6.addNeighbouringStop(stop5);

        stop1.addNeighbouringStop(stop6);
        stop6.addNeighbouringStop(stop1);

        stop6.addNeighbouringStop(stop3);
        stop3.addNeighbouringStop(stop6);

        p1 = new Passenger("p1", stop3);
        p2 = new Passenger("p2", stop3);
        p3 = new Passenger("p3", stop2);
        p4 = new Passenger("p4", null);

        busRoute = new BusRoute("busRoute1", 1);

        bus = new Bus(110, 5, busRoute, "BUS110");

        stop1.transportArrive(bus);
    }

    @Test
    public void addPassenger() {
        stop1.addPassenger(null);
        assertEquals(0, stop1.getWaitingPassengers().size());

        stop1.addPassenger(p4);
        assertEquals(1, stop1.getWaitingPassengers().size());

        stop1.addPassenger(p1);
        stop1.addPassenger(p2);
        stop1.addPassenger(p3);
        assertEquals(4, stop1.getWaitingPassengers().size());
    }

    @Test
    public void transportDepart() {
        stop1.addPassenger(p1);
        stop1.addPassenger(p2);
        stop1.addPassenger(p3);
        stop1.addPassenger(p4);
        assertEquals(4, stop1.getWaitingPassengers().size());
        assertTrue(stop1.isAtStop(bus));

        stop1.transportDepart(bus, stop0);
//
//        assertEquals(1, stop1.getWaitingPassengers().size());
    }

    @Test
    public void getRoutingTable() {
    }
}