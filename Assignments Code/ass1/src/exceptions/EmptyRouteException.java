package exceptions;

import java.io.Serializable;

/**
 * Exception thrown when a PublicTransport is added to a Route with no Stops.
 */
public class EmptyRouteException extends TransportException implements Serializable {
}
