package routes;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrainRouteTest {

    @Test
    public void getType() {
        Route trainRoute = new TrainRoute("Beenleigh-Brisbane",1314);
        assertEquals("train", trainRoute.getType());
    }
}