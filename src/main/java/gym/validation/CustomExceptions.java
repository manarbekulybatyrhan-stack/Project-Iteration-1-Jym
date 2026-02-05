package gym.validation;

public class CustomExceptions {
    public static class InvalidEmailException extends Exception {
        public InvalidEmailException(String message) { super(message); }
    }
    public static class InvalidPhoneException extends Exception {
        public InvalidPhoneException(String message) { super(message); }
    }
    public static class InvalidNameException extends Exception {
        public InvalidNameException(String message) { super(message); }
    }
    public static class InvalidDurationException extends Exception {
        public InvalidDurationException(String message) { super(message); }
    }
    public static class InvalidPriceException extends Exception { // Добавлено
        public InvalidPriceException(String message) { super(message); }
    }
    public static class UnauthorizedAccessException extends Exception {
        public UnauthorizedAccessException(String message) { super(message); }
    }
}