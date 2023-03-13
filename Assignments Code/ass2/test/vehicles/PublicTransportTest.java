package vehicles;

import network.Network;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PublicTransportTest {
    private final String validFile = "validFromSpec.txt";
    Network network;

    @Before
    public void setUp() throws Exception {
        network = new Network(validFile);
    }

    @Test
    public void encode() {
        List<PublicTransport> vehicles = network.getVehicles();
        List<String> encodedVehicles = new ArrayList<>();
        List<String> wantedVehicles = new ArrayList<>();

        for (PublicTransport v : vehicles) {
            encodedVehicles.add(v.encode());
        }

        wantedVehicles.add("train,123,30,1,2");
        wantedVehicles.add("train,42,60,1,3");
        wantedVehicles.add("bus,412,20,2,ABC123");

        assertArrayEquals(wantedVehicles, encodedVehicles);
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