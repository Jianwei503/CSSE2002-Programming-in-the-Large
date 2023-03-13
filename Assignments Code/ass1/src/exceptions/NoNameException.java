package exceptions;

import java.io.Serializable;

/**
 * Exception thrown when a Stop is created with a null or empty name.
 */
public class NoNameException extends RuntimeException implements Serializable {
}
