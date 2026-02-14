package gym.model;

public enum SessionCategory {
    CARDIO("Cardio", "Cardiovascular training - running, cycling, rowing"),
    STRENGTH("Strength", "Strength and muscle building - weights, resistance"),
    FLEXIBILITY("Flexibility", "Yoga and stretching - flexibility training"),
    HIIT("HIIT", "High-Intensity Interval Training"),
    PILATES("Pilates", "Core strength training"),
    BOXING("Boxing", "Boxing and combat training"),
    SWIMMING("Swimming", "Swimming and water aerobics");
    
    private final String name;
    private final String description;
    
    SessionCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static SessionCategory fromString(String value) {
        for (SessionCategory category : SessionCategory.values()) {
            if (category.name.equalsIgnoreCase(value)) {
                return category;
            }
        }
        return CARDIO; 
    }
}
