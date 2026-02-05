package gym.validation;

import java.util.function.Predicate;
import java.util.regex.Pattern;
import gym.validation.CustomExceptions.*; // Импортируем вложенные классы

public class ValidationService {
    private static ValidationService instance;

    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^[0-9]{10,15}$");

    private ValidationService() {}

    public static ValidationService getInstance() {
        if (instance == null) {
            instance = new ValidationService();
        }
        return instance;
    }

    public boolean validateEmail(String email) throws InvalidEmailException {
        Predicate<String> emailValidator = e -> EMAIL_PATTERN.matcher(e).matches();
        if (!emailValidator.test(email)) {
            throw new InvalidEmailException("Invalid email format: " + email);
        }
        return true;
    }

    public boolean validatePhone(String phone) throws InvalidPhoneException {
        Predicate<String> phoneValidator = p -> PHONE_PATTERN.matcher(p).matches();
        if (!phoneValidator.test(phone)) {
            throw new InvalidPhoneException("Invalid phone format: " + phone);
        }
        return true;
    }

    public boolean validateName(String name) throws InvalidNameException {
        Predicate<String> nameValidator = n -> n != null && !n.trim().isEmpty() && n.length() >= 2;
        if (!nameValidator.test(name)) {
            throw new InvalidNameException("Invalid name: " + name);
        }
        return true;
    }

    public boolean validatePrice(double price) throws InvalidPriceException {
        Predicate<Double> priceValidator = p -> p > 0;
        if (!priceValidator.test(price)) {
            throw new InvalidPriceException("Price must be positive: " + price);
        }
        return true;
    }

    public boolean validateDuration(int duration) throws InvalidDurationException {
        Predicate<Integer> durationValidator = d -> d > 0 && d <= 240;
        if (!durationValidator.test(duration)) {
            throw new InvalidDurationException("Duration must be 1-240 minutes: " + duration);
        }
        return true;
    }
}