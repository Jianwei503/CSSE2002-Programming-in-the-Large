package passengers;

import stops.Stop;

/**
 * A passenger that pays concession fares. Concession fares require a concession id.
 */
public class ConcessionPassenger extends Passenger {
    private int concessionId;
    private boolean bool = true;

    /**
     * Construct a new concession fare passenger with the given name and concessionId.
     * @see Passenger#Passenger(String, Stop)
     * @param name The name of the passenger.
     * @param destination The destination of the passenger.
     * @param concessionId Identifying number of the passenger's concession card.
     */
    public ConcessionPassenger (String name, Stop destination, int concessionId) {
        super(name, destination);
        this.concessionId = concessionId;
    }

    /**
     * Sets the concession fare to be expired, and thus invalid.
     * {@link ConcessionPassenger#isValid()} returns false.
     */
    public void expire () {
        bool = false;
    }

    /**
     * Attempts to renew this concession passenger's fares with the given id.
     * @param newId The ID of the renewed concession card.
     */
    public void renew (int newId) {
        this.concessionId = newId;
        bool = true;
    }

    /**
     * Returns true if and only if the stored concessionId is valid.
     * <p>
     * In this transportation network, a valid concessionId begins with the digits '42',
     * should be positive, and should be a minimum of six digits in length
     * </p>
     * @return True if concession fares have not expired (are valid), false otherwise.
     */
    public boolean isValid () {
        if (!bool) {
            return false;
        }
        String id = Integer.toString(concessionId);
        return id.length() >= 6 && id.startsWith("42");
    }
}
