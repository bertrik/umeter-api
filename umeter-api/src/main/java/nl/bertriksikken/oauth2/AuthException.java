package nl.bertriksikken.oauth2;

public final class AuthException extends Exception {

    private static final long serialVersionUID = 1L;
    
    public AuthException(String message) {
        super(message);
    }
    
}
