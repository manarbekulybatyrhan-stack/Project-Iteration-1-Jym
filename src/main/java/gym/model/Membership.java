package gym.model;

public class Membership {

    private int id;
    private String type;
    private String category;
    private int durationMonths;
    private double price;

    public Membership() {}

    public Membership(int id, String type, String category, int durationMonths, double price) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.durationMonths = durationMonths;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getDurationMonths() {
        return durationMonths;
    }

    public void setDurationMonths(int durationMonths) {
        this.durationMonths = durationMonths;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
