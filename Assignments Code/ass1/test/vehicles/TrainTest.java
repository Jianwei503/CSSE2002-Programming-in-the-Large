package vehicles;

import org.junit.Before;
import org.junit.Test;
import routes.Route;
import routes.TrainRoute;

import static org.junit.Assert.*;

public class TrainTest {
    Train train;
    Route trainRoute = new TrainRoute("Beenleigh-Brisbane",1314);

    @Before
    public void setUp() throws Exception {
        train = new Train(15,450,trainRoute,15);
    }

    @Test
    public void getCarriageCount() {
        assertEquals(15,train.getCarriageCount());
    }
}