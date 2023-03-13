package routes;

import org.junit.Test;

import static org.junit.Assert.*;

public class FerryRouteTest {

    @Test
    public void getType() {
        Route ferryRoute = new FerryRoute("UQ-City",326);
        assertEquals("ferry",ferryRoute.getType());
    }
}