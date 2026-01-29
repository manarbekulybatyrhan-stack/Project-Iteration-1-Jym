package gym.model;

import java.time.LocalDate;

public class Member {
    private int id;
    private String name;
    private String email;
    private String phone;
    private int membershipId;
    private LocalDate joinDate;
    private LocalDate expiryDate;

    public Member() {}

    // Конструктор на 7 параметров
    public Member(int id, String name, String email, String phone, int membershipId, LocalDate joinDate, LocalDate expiryDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.membershipId = membershipId;
        this.joinDate = joinDate;
        this.expiryDate = expiryDate;
    }

    // НОВЫЙ Конструктор на 5 параметров (для контроллера)
    public Member(int id, String name, String email, String phone, int membershipId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.membershipId = membershipId;
        this.joinDate = LocalDate.now();
        this.expiryDate = LocalDate.now().plusMonths(1);
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public int getMembershipId() { return membershipId; }
    public void setMembershipId(int membershipId) { this.membershipId = membershipId; }
    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    // Проверка активности для репозитория
    public boolean isActive() {
        return expiryDate != null && expiryDate.isAfter(LocalDate.now());
    }

    public boolean isValidEmail() {
        return this.email != null && this.email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public boolean isValidPhone() {
        return this.phone != null && this.phone.matches("^[0-9]{10,15}$");
    }
}