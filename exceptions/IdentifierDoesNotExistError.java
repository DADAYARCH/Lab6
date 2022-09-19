package exceptions;

public class IdentifierDoesNotExistError extends Exception {
    public IdentifierDoesNotExistError() {
        super();
    }

    public static class ParsingError extends Exception {
        public ParsingError(String message) {
            super(message);
        }

        public ParsingError() {
            super();
        }
    }
}
