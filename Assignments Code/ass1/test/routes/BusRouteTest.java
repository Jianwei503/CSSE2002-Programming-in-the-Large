package routes;

import org.junit.Test;

import static org.junit.Assert.*;

public class BusRouteTest {

    @Test
    public void getType() {
        Route busRoute = new BusRoute("AcaciaRidge\n-\rWoolloongabba",115);
        assertEquals("bus",busRoute.getType());
    }
}