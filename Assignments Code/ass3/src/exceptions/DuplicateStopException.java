package exceptions;

/**
 * Exception thrown when a duplicate Stop (as defined by
 * {@link stops.Stop#equals(Object)}) is added to a Network.
 */
public class DuplicateStopException extends TransportException {
}
