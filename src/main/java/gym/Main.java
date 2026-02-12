package gym;

import gym.controller.MemberController;
import gym.controller.MembershipController;
import gym.controller.TrainerController;
import gym.controller.TrainingSessionController;
import gym.security.Role;
import gym.security.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    private static final MembershipController membershipController = new MembershipController();
    private static final TrainerController trainerController = new TrainerController();
    private static final MemberController memberController = new MemberController();
    private static final TrainingSessionController sessionController = new TrainingSessionController();

    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static User currentUser;

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("✗ Enter a valid integer number.");
            }
        }
    }

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            int v = readInt(prompt);
            if (v < min || v > max) {
                System.out.println("✗ Enter a number from " + min + " to " + max + ".");
                continue;
            }
            return v;
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("✗ Enter a valid decimal number.");
            }
        }
    }

    private static LocalDateTime readDateTime(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            try {
                return LocalDateTime.parse(s, DT_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("✗ Wrong format. Use: yyyy-MM-dd HH:mm (example: 2026-02-12 14:30)");
            }
        }
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("✗ Value cannot be empty.");
        }
    }

    private static void login() {
        String username = readNonEmpty("Enter username: ");

        System.out.println("Select role:");
        System.out.println("1. ADMIN");
        System.out.println("2. MANAGER");
        System.out.println("3. TRAINER");
        System.out.println("4. MEMBER");

        int roleChoice = readInt("Enter role number: ", 1, 4);

        Role role = switch (roleChoice) {
            case 1 -> Role.ADMIN;
            case 2 -> Role.MANAGER;
            case 3 -> Role.TRAINER;
            default -> Role.MEMBER;
        };

        currentUser = new User(1, username, "password", role);
        System.out.println("✓ Logged in as " + username + " (" + role + ")");
    }

    public static void main(String[] args) {
        System.out.println("=== GYM MANAGEMENT SYSTEM ===");
        login();

        while (true) {
            displayMainMenu();
            int choice = readInt("Enter choice: ", 1, 5);

            switch (choice) {
                case 1 -> membershipMenu();
                case 2 -> trainerMenu();
                case 3 -> memberMenu();
                case 4 -> sessionMenu();
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Membership Management");
        System.out.println("2. Trainer Management");
        System.out.println("3. Member Management");
        System.out.println("4. Training Session Management");
        System.out.println("5. Exit");
    }

    private static void membershipMenu() {
        while (true) {
            System.out.println("\n--- MEMBERSHIP MENU ---");
            System.out.println("1. Add Membership");
            System.out.println("2. View All Memberships");
            System.out.println("3. Update Membership");
            System.out.println("4. Delete Membership");
            System.out.println("5. Back");

            int choice = readInt("Enter choice: ", 1, 5);

            switch (choice) {
                case 1 -> {
                    String type = readNonEmpty("Type: ");
                    int duration = readInt("Duration (months): ");
                    double price = readDouble("Price: ");
                    membershipController.addMembership(type, duration, price);
                }
                case 2 -> membershipController.displayAllMemberships();
                case 3 -> {
                    int id = readInt("Membership ID: ");
                    String type = readNonEmpty("New type: ");
                    int duration = readInt("New duration: ");
                    double price = readDouble("New price: ");
                    membershipController.updateMembership(id, type, duration, price);
                }
                case 4 -> {
                    int id = readInt("Membership ID: ");
                    membershipController.deleteMembership(id);
                }
                case 5 -> {
                    return;
                }
            }
        }
    }

    private static void trainerMenu() {
        while (true) {
            System.out.println("\n--- TRAINER MENU ---");
            System.out.println("1. Add Trainer");
            System.out.println("2. View All Trainers");
            System.out.println("3. Update Trainer");
            System.out.println("4. Delete Trainer");
            System.out.println("5. Back");

            int choice = readInt("Enter choice: ", 1, 5);

            switch (choice) {
                case 1 -> {
                    String name = readNonEmpty("Name: ");
                    String spec = readNonEmpty("Specialization: ");
                    String email = readNonEmpty("Email: ");
                    String phone = readNonEmpty("Phone: ");
                    trainerController.addTrainer(name, spec, email, phone);
                }
                case 2 -> trainerController.displayAllTrainers();
                case 3 -> {
                    int id = readInt("Trainer ID: ");
                    String name = readNonEmpty("New name: ");
                    String spec = readNonEmpty("New specialization: ");
                    String email = readNonEmpty("New email: ");
                    String phone = readNonEmpty("New phone: ");
                    trainerController.updateTrainer(id, name, spec, email, phone);
                }
                case 4 -> {
                    int id = readInt("Trainer ID: ");
                    trainerController.deleteTrainer(id);
                }
                case 5 -> {
                    return;
                }
            }
        }
    }

    private static void memberMenu() {
        while (true) {
            System.out.println("\n--- MEMBER MENU ---");
            System.out.println("1. Register Member");
            System.out.println("2. View All Members");
            System.out.println("3. Back");

            int choice = readInt("Enter choice: ", 1, 3);

            switch (choice) {
                case 1 -> {
                    String name = readNonEmpty("Name: ");
                    String email = readNonEmpty("Email: ");
                    String phone = readNonEmpty("Phone: ");
                    int membershipId = readInt("Membership ID: ");
                    memberController.registerMember(name, email, phone, membershipId);
                }
                case 2 -> memberController.displayAllMembers();
                case 3 -> {
                    return;
                }
            }
        }
    }

    private static void sessionMenu() {
        while (true) {
            System.out.println("\n--- SESSION MENU ---");
            System.out.println("1. Book Session");
            System.out.println("2. View All Sessions");
            System.out.println("3. Back");

            int choice = readInt("Enter choice: ", 1, 3);

            switch (choice) {
                case 1 -> {
                    int memberId = readInt("Member ID: ");
                    int trainerId = readInt("Trainer ID: ");
                    LocalDateTime dateTime = readDateTime("Date (yyyy-MM-dd HH:mm): ");
                    int duration = readInt("Duration (minutes): ");
                    String type = readNonEmpty("Type: ");
                    sessionController.bookSession(memberId, trainerId, dateTime, duration, type);
                }
                case 2 -> sessionController.displayAllSessions();
                case 3 -> {
                    return;
                }
            }
        }
    }
}
