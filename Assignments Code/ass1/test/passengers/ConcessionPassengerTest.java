package passengers;

import org.junit.Before;
import org.junit.Test;
import stops.Stop;

import static org.junit.Assert.*;

public class ConcessionPassengerTest {
    ConcessionPassenger concessionPassenger;

    @Before
    public void setUp() throws Exception {
        Stop stop = new Stop("UQ Lakes",23,56);
        //generate a Passenger instance with name and destination
        concessionPassenger = new ConcessionPassenger("\rAbel\nSmith",stop,421314);
    }

    @Test
    public void expire() {
        assertTrue(concessionPassenger.isValid());
        concessionPassenger.expire();
        assertFalse(concessionPassenger.isValid());
    }

    @Test
    public void renew() {
        concessionPassenger.expire();
        assertFalse(concessionPassenger.isValid());

        concessionPassenger.renew(421413);
        assertTrue(concessionPassenger.isValid());
    }

    @Test
    public void isValid() {
        assertTrue(concessionPassenger.isValid());

        concessionPassenger.renew(431413);
        assertFalse(concessionPassenger.isValid());
    }
}