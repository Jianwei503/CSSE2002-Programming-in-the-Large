package routes;

import network.Network;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RouteTest {
    private final String validFile = "validFromSpec.txt";
    Network network;

    @Before
    public void setUp() throws Exception {
        network = new Network(validFile);
    }

    @Test
    public void encode() {
        List<Route> routes = network.getRoutes();
        List<String> encodedRoutes = new ArrayList<>();
        List<String> wantedRoutes = new ArrayList<>();

        for (Route r : routes) {
            encodedRoutes.add(r.encode());
        }

        wantedRoutes.add("train,red,1:stop0|stop2|stop1");
        wantedRoutes.add("bus,blue,2:stop1|stop3|stop0");

        assertArrayEquals(wantedRoutes, encodedRoutes);
    }

    private void assertArrayEquals(List<String> wantedString,
                                   List<String> encodedString) {
        int size1 = wantedString.size();
        int size2 = encodedString.size();

        assertEquals(size1, size2);

        for (int i = 0; i < size1; i++) {
            assertEquals(wantedString.get(i), encodedString.get(i));
        }
    }
}