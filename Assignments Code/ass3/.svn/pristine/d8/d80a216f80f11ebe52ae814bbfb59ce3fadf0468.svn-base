package stops;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class RoutingTableTest {
    Stop stop0, stop1, stop2, stop3, stop4, stop5, stop6;

    @Before
    public void setUp() throws Exception {
        stop0 = new Stop("stop0", 0, 0);
        stop1 = new Stop("stop1", -1, 0);
        stop2 = new Stop("stop2", 2, 0);
        stop3 = new Stop("stop3", 3, 0);
        stop4 = new Stop("stop4", 0, 1);
        stop5 = new Stop("stop5", 0, -1);
        stop6 = new Stop("stop6", 0, -2);

        stop0.addNeighbouringStop(stop1);
        stop1.addNeighbouringStop(stop0);

        stop0.addNeighbouringStop(stop2);
        stop2.addNeighbouringStop(stop0);

        stop2.addNeighbouringStop(stop3);
        stop3.addNeighbouringStop(stop2);

        stop0.addNeighbouringStop(stop4);
        stop4.addNeighbouringStop(stop0);

        stop0.addNeighbouringStop(stop5);
        stop5.addNeighbouringStop(stop0);

        stop5.addNeighbouringStop(stop6);
        stop6.addNeighbouringStop(stop5);

        stop1.addNeighbouringStop(stop6);
        stop6.addNeighbouringStop(stop1);

        stop6.addNeighbouringStop(stop3);
        stop3.addNeighbouringStop(stop6);
    }

    @Test
    public void addNeighbour() {
        Stop stop7 = new Stop("stop7", 4, 0);
        stop7.addNeighbouringStop(stop3);
        stop3.addNeighbouringStop(stop7);

        assertEquals("stop7", stop3.getRoutingTable().nextStop(stop7).getName());
        assertEquals("stop3", stop7.getRoutingTable().nextStop(stop3).getName());
    }

    @Test
    public void addNeighbourExistedStop() {
        assertEquals("stop0",stop1.getRoutingTable().nextStop(stop3).getName());

        stop1.getRoutingTable().addNeighbour(stop3);
        assertEquals("stop0",stop1.getRoutingTable().nextStop(stop3).getName());

    }

    @Test
    public void addOrUpdateEntry() {
        Stop stop7 = new Stop("stop7", 4, 0);
        boolean changed =
                stop2.getRoutingTable().addOrUpdateEntry(stop7, 2, stop3);
        String name = stop2.getRoutingTable().nextStop(stop7).getName();

        assertTrue(changed);
        assertEquals("stop3", name);
    }

    @Test
    public void addOrUpdateEntryExistedEntryLowerCost() {
        boolean changed = stop2.getRoutingTable().addOrUpdateEntry(stop3, 0, stop3);
        int cost = stop2.getRoutingTable().costTo(stop3);

        assertTrue(changed);
        assertEquals(0, cost);
    }

    @Test
    public void addOrUpdateEntryExistedEntryHigherCost() {
        boolean changed = stop2.getRoutingTable().addOrUpdateEntry(stop3, 5, stop3);
        int cost = stop2.getRoutingTable().costTo(stop3);

        assertFalse(changed);
        assertEquals(1, cost);
    }

    @Test
    public void costTo() {
        assertEquals(1, stop3.getRoutingTable().costTo(stop2));
        assertEquals(2, stop0.getRoutingTable().costTo(stop2));
        assertEquals(3, stop1.getRoutingTable().costTo(stop6));
        assertEquals(4, stop1.getRoutingTable().costTo(stop3));
        assertEquals(5, stop3.getRoutingTable().costTo(stop6));
        assertEquals(2, stop1.getRoutingTable().costTo(stop4));
        assertEquals(2, stop1.getRoutingTable().costTo(stop5));
    }

    @Test
    public void costToNotExistedStop() {
        Stop stop7 = new Stop("stop7", 4, 0);

        assertEquals(Integer.MAX_VALUE, stop2.getRoutingTable().costTo(stop7));
    }

    @Test
    public void getCosts() {
        Map<Stop, Integer> costs = stop1.getRoutingTable().getCosts();

        assertEquals(7, costs.size());
        assertEquals(1, (int)costs.get(stop0));
        assertEquals(3, (int)costs.get(stop2));
        assertEquals(4, (int)costs.get(stop3));
        assertEquals(2, (int)costs.get(stop4));
        assertEquals(2, (int)costs.get(stop5));
        assertEquals(3, (int)costs.get(stop6));
    }

    @Test
    public void getStop() {
        assertEquals(stop0, stop0.getRoutingTable().getStop());
        assertEquals(stop1, stop1.getRoutingTable().getStop());
        assertEquals(stop2, stop2.getRoutingTable().getStop());
        assertEquals(stop3, stop3.getRoutingTable().getStop());
        assertNotEquals(stop0, stop6.getRoutingTable().getStop());
    }

    @Test
    public void nextStop() {
        Stop next0 = stop1.getRoutingTable().nextStop(stop3);
        Stop next1 = stop1.getRoutingTable().nextStop(stop1);
        Stop next2 = stop0.getRoutingTable().nextStop(stop6);
        Stop next3 = stop3.getRoutingTable().nextStop(stop6);

        assertEquals(stop0, next0);
        assertEquals(stop1, next1);
        assertEquals(stop5, next2);
        assertEquals(stop2, next3);
    }

    @Test
    public void nextStopNullStop() {
        Stop next = stop1.getRoutingTable().nextStop(null);

        assertNull(next);
    }

    @Test
    public void nextStopNotExistedStop() {
        Stop stop7 = new Stop("stop7", 4, 0);
        Stop next = stop1.getRoutingTable().nextStop(stop7);

        assertNull(next);
    }

    @Test
    public void synchronise() {
        Stop stop7 = new Stop("stop7", 4, 0);
        stop7.addNeighbouringStop(stop3);
        stop3.addNeighbouringStop(stop7);

        assertEquals(5, stop1.getRoutingTable().costTo(stop7));
        assertEquals(4, stop0.getRoutingTable().costTo(stop7));
        assertEquals(2, stop2.getRoutingTable().costTo(stop7));
        assertEquals(5, stop4.getRoutingTable().costTo(stop7));
        assertEquals(5, stop5.getRoutingTable().costTo(stop7));
        assertEquals(6, stop6.getRoutingTable().costTo(stop7));
    }

    @Test
    public void transferEntries() {
        Stop stop7 = new Stop("stop7", 4, 0);

        assertEquals(1,stop7.getRoutingTable().getCosts().size());

        boolean changed = stop3.getRoutingTable().transferEntries(stop7);

        assertEquals(8,stop7.getRoutingTable().getCosts().size());
        assertTrue(changed);
    }

    @Test
    public void transferEntriesLowerCost() {
        Stop stop7 = new Stop("stop7", 4, 0);
        stop7.getRoutingTable().addOrUpdateEntry(stop0, 1, stop3);
        boolean changed = stop7.getRoutingTable().transferEntries(stop3);

        assertTrue(changed);
        assertEquals(8,stop3.getRoutingTable().getCosts().size());
        assertEquals(2, stop3.getRoutingTable().costTo(stop0));
    }

    @Test
    public void transgerEntriesHigherCost() {
        stop1.getRoutingTable().addOrUpdateEntry(stop0, 5, stop0);
        boolean changed = stop1.getRoutingTable().transferEntries(stop3);

        assertFalse(changed);
        assertEquals(7,stop3.getRoutingTable().getCosts().size());
        assertEquals(3, stop3.getRoutingTable().costTo(stop0));
    }

    @Test
    public void traverseNetwork() {
        List<Stop> reachable = stop1.getRoutingTable().traverseNetwork();
        Set<Stop> set = new HashSet<>();
        set.addAll(reachable);

        assertEquals(7, set.size());
        assertTrue(set.contains(stop1));
        assertTrue(set.contains(stop0));
        assertTrue(set.contains(stop2));
        assertTrue(set.contains(stop3));
        assertTrue(set.contains(stop4));
        assertTrue(set.contains(stop5));
        assertTrue(set.contains(stop6));
    }

    @Test
    public void traverseNetworkCannotReachStop() {
        Stop stop7 = new Stop("stop7", 4, 0);
        List<Stop> reachable = stop1.getRoutingTable().traverseNetwork();

        assertFalse(reachable.contains(stop7));
    }

    @Test
    public void traversenetworkSingleStop() {
        Stop stop7 = new Stop("stop7", 4, 0);
        List<Stop> reachable = stop7.getRoutingTable().traverseNetwork();
        Set<Stop> set = new HashSet<>();
        set.addAll(reachable);

        assertEquals(1, set.size());
        assertTrue(set.contains(stop7));
    }
}