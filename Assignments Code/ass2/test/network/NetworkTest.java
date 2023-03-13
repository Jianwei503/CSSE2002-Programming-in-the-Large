package network;

import exceptions.TransportFormatException;
import org.junit.Before;
import org.junit.Test;
import routes.BusRoute;
import routes.Route;
import stops.Stop;
import vehicles.Bus;
import vehicles.PublicTransport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class NetworkTest {
    private final String VALID_FILE_NAME = "validFromSpec.txt";
    Network network;

    @Before
    public void setUp() throws Exception {
        network = new Network(VALID_FILE_NAME);
    }

    @Test(expected = TransportFormatException.class)
    public void invalidBlankLines()
            throws IOException, TransportFormatException {
        Network network = new Network("invalidBlankLines.txt");
    }

    @Test(expected = TransportFormatException.class)
    public void invalidRouteEmptyStopNames()
            throws IOException, TransportFormatException {
        Network network = new Network("invalidRouteEmptyStopNames.txt");
    }

    @Test(expected = TransportFormatException.class)
    public void invalidRouteStops()
            throws IOException, TransportFormatException {
        Network network = new Network("invalidRouteStops.txt");
    }

    @Test(expected = TransportFormatException.class)
    public void invalidRouteTypes()
            throws IOException, TransportFormatException {
        Network network = new Network("invalidRouteTypes.txt");
    }

    @Test(expected = TransportFormatException.class)
    public void invalidStopCount()
            throws IOException, TransportFormatException {
        Network network = new Network("invalidStopCount.txt");
    }

    @Test(expected = TransportFormatException.class)
    public void invalidStopDelimiters()
            throws IOException, TransportFormatException {
        Network network = new Network("invalidStopDelimiters.txt");
    }

    @Test(expected = TransportFormatException.class)
    public void invalidTransportIntegers()
            throws IOException, TransportFormatException {
        Network network = new Network("invalidTransportIntegers.txt");
    }

    @Test(expected = TransportFormatException.class)
    public void invalidTransportPartsMissing()
            throws IOException, TransportFormatException {
        Network network = new Network("invalidTransportPartsMissing.txt");
    }

    @Test(expected = TransportFormatException.class)
    public void validEmptyRoute()
            throws IOException, TransportFormatException {
        Network network = new Network("validEmptyRoute.txt");
    }

    @Test(expected = TransportFormatException.class)
    public void invalidRouteStopNotInStops()
            throws IOException, TransportFormatException {
        Network network = new Network("invalidRouteStopNotInStops.txt");
    }

    @Test(expected = TransportFormatException.class)
    public void invalidVehicleRouteNotInRoutes()
            throws IOException, TransportFormatException {
        Network network = new Network("invalidVehicleRouteNotInRoutes.txt");
    }

    @Test(expected = TransportFormatException.class)
    public void invalidVehicleTypeNotMatchRouteType()
            throws IOException, TransportFormatException {
        Network network =
                new Network("invalidVehicleTypeNotMatchRouteType.txt");
    }

    @Test(expected = IOException.class)
    public void fileNotFound() throws IOException, TransportFormatException {
        Network network = new Network("validFromSpec1.txt");
    }

    @Test(expected = IOException.class)
    public void readNullFile() throws IOException, TransportFormatException {
        Network network = new Network(null);
    }

    @Test
    public void validFromSpecMoreElements()
            throws IOException, TransportFormatException {
        Network network = new Network("validFromSpecMoreElements.txt");

        assertEquals(5, network.getStops().size());
        assertEquals(3, network.getRoutes().size());
        assertEquals(4, network.getVehicles().size());
    }

    @Test
    public void addRoute() {
        Route newRoute = new BusRoute("Inala-City", 115);
        network.addRoute(newRoute);

        assertEquals(3, network.getRoutes().size());
    }

    @Test
    public void addRouteNullValue() {
        network.addRoute(null);

        assertEquals(2, network.getRoutes().size());
    }

    @Test
    public void addStop() {
        Stop newStop = new Stop("stop110", 110, -127);
        network.addStop(newStop);

        assertEquals(5, network.getStops().size());
    }

    @Test
    public void addStopNullValue() {
        network.addStop(null);

        assertEquals(4, network.getStops().size());
    }

    @Test
    public void addStops() {
        Stop newStop1 = new Stop("stop110", 110, -127);
        Stop newStop2 = new Stop("stop115", 115, -120);

        List<Stop> list = new ArrayList<>();
        list.add(newStop1);
        list.add(newStop2);
        network.addStops(list);

        assertEquals(6, network.getStops().size());
    }

    @Test
    public void addStopsContainsNull() {
        Stop newStop = new Stop("stop110", 110, -127);

        List<Stop> list = new ArrayList<>();
        list.add(newStop);
        list.add(null);
        network.addStops(list);

        assertEquals(4, network.getStops().size());
    }

    @Test
    public void addVehicle() {
        Route busRoute = network.getRoutes().get(1);
        PublicTransport newBus = new Bus(110, 30, busRoute, "BUS110");
        network.addVehicle(newBus);

        assertEquals(4, network.getVehicles().size());
    }

    @Test
    public void addVehicleNullValue() {
        network.addVehicle(null);

        assertEquals(3, network.getVehicles().size());
    }

    @Test
    public void getStops() {
        List<Stop> stops = network.getStops();

        assertEquals(4, stops.size());

        // Modifying the returned list should not result in changes to
        // the internal state of the class.
        stops.clear();
        assertEquals(4, network.getStops().size());
    }

    @Test
    public void getRoutes() {
        List<Route> routes = network.getRoutes();

        assertEquals(2, routes.size());

        // Modifying the returned list should not result in changes to
        // the internal state of the class.
        routes.clear();
        assertEquals(2, network.getRoutes().size());
    }

    @Test
    public void getVehicles() {
        List<PublicTransport> vehicles = network.getVehicles();

        assertEquals(3, vehicles.size());

        // Modifying the returned list should not result in changes to
        // the internal state of the class.
        vehicles.clear();
        assertEquals(3, network.getVehicles().size());
    }

    @Test
    public void save() throws IOException, TransportFormatException {
        network.save("validSavedFile.txt");

        Network network1 = new Network("validSavedFile.txt");

        assertEquals(4, network1.getStops().size());
        assertEquals(2, network1.getRoutes().size());
        assertEquals(3, network1.getVehicles().size());
    }

    @Test
    public void saveAndReadEmptyNetwork() throws IOException, TransportFormatException {
        Network emptyNetwork = new Network();
        emptyNetwork.save("validSavedFile.txt");

        Network emptyNetwork1 = new Network("validSavedFile.txt");

        assertEquals(0, emptyNetwork1.getStops().size());
        assertEquals(0, emptyNetwork1.getRoutes().size());
        assertEquals(0, emptyNetwork1.getVehicles().size());
    }
}