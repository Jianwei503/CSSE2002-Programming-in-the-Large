package exceptions;

import java.io.Serializable;

/**
 * Exception thrown when a subclass of PublicTransport is added to a Route for
 * a different type of transport. i.e. Train added to BusRoute
 */
public class IncompatibleTypeException extends TransportException implements Serializable {
}
