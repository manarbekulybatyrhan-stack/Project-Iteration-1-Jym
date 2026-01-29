package gym.model;

public enum MembershipCategory {
    BASIC("Basic", "Access to gym facilities during business hours", 1, 29.99),
    PREMIUM("Premium", "Gym + Group classes + Locker access", 1, 49.99),
    VIP("VIP", "Everything + Personal trainer (2 sessions/week)", 1, 99.99),
    CORPORATE("Corporate", "Team packages for organizations", 1, 399.99),
    STUDENT("Student", "Discounted plan for students", 1, 19.99);
    
    private final String name;
    private final String description;
    private final int durationMonths;
    private final double price;
    
    MembershipCategory(String name, String description, 
                       int durationMonths, double price) {
        this.name = name;
        this.description = description;
        this.durationMonths = durationMonths;
        this.price = price;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getDurationMonths() {
        return durationMonths;
    }
    
    public double getPrice() {
        return price;
    }
    
    public static MembershipCategory fromString(String value) {
        for (MembershipCategory category : MembershipCategory.values()) {
            if (category.name.equalsIgnoreCase(value)) {
                return category;
            }
        }
        return BASIC; // Default
    }
}
