package vehicles;

import org.junit.Before;
import org.junit.Test;
import routes.BusRoute;
import routes.Route;
import stops.Stop;

import static org.junit.Assert.*;

public class BusTest {
    Bus bus;
    Route busRoute = new BusRoute( "AcaciaRidge-Woolloongabba",110);

    @Before
    public void setUp() throws Exception {
        busRoute.addStop( new Stop("Acacia Ridge",-27,153 ) );
        bus = new Bus(1,10,busRoute,"078TZL");
    }

    @Test
    public void getRegistrationNumber() {
        assertEquals("078TZL",bus.getRegistrationNumber());
    }
}