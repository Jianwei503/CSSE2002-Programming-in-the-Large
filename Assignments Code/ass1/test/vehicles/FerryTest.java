package vehicles;

import org.junit.Before;
import org.junit.Test;
import routes.BusRoute;
import routes.FerryRoute;
import routes.Route;

import static org.junit.Assert.*;

public class FerryTest {
    Ferry ferry;
    Route ferryRoute = new FerryRoute( "UQ-City",1314);

    @Before
    public void setUp() throws Exception {
        ferry = new Ferry(11,20,ferryRoute,"");
    }

    @Test
    public void getFerryType() {
        assertEquals("CityCat",ferry.getFerryType());
    }
}