package gym.security;

import java.util.Arrays;
import java.util.List;


public enum Role {
    ADMIN(Arrays.asList("VIEW_ALL", "ADD", "DELETE", "UPDATE")),
    MANAGER(Arrays.asList("VIEW_ALL", "ADD", "UPDATE")),
    TRAINER(Arrays.asList("VIEW_MEMBERS", "UPDATE_SESSIONS")),
    MEMBER(Arrays.asList("VIEW_OWN_SESSIONS", "BOOK_SESSION"));

    private final List<String> permissions;

    Role(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }
}
