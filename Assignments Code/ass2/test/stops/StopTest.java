package stops;

import network.Network;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StopTest {
    private final String validFile = "validFromSpec.txt";
    Network network;

    @Before
    public void setUp() throws Exception {
        network = new Network(validFile);
    }

    @Test
    public void encode() {
        List<Stop> stops = network.getStops();
        List<String> encodedStops = new ArrayList<>();
        List<String> wantedStops = new ArrayList<>();

        for (Stop s : stops) {
            encodedStops.add(s.encode());
        }

        wantedStops.add("stop0:0:1");
        wantedStops.add("stop1:-1:0");
        wantedStops.add("stop2:4:2");
        wantedStops.add("stop3:2:-8");

        assertArrayEquals(wantedStops, encodedStops);
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
