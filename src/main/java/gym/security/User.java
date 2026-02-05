package gym.security;


public class User {
    private int userId;
    private String username;
    private String password;
    private Role role;
    private boolean active;

    public User(int userId, String username, String password, Role role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = true;
    }

    public boolean hasPermission(String action) {
        return role.getPermissions().stream()
                .anyMatch(perm -> perm.equals(action));
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}

