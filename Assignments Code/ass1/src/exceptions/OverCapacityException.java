package exceptions;

import java.io.Serializable;

/**
 * Exception thrown when a Passenger is added to a PublicTransport which has reached capacity.
 * (i.e. PublicTransport.passengerCount() >= PublicTransport.getCapacity())
 */
public class OverCapacityException extends TransportException implements Serializable {
}
