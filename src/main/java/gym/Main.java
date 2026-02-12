package gym;

import gym.controller.MemberController;
import gym.controller.MembershipController;
import gym.controller.TrainerController;
import gym.controller.TrainingSessionController;
import gym.repository.UserRepository;
import gym.repository.impl.UserRepositoryImpl;
import gym.security.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private static MembershipController membershipController = new MembershipController();
    private static TrainerController trainerController = new TrainerController();
    private static MemberController memberController = new MemberController();
    private static TrainingSessionController sessionController = new TrainingSessionController();
    private static UserRepository userRepository = new UserRepositoryImpl();
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
        System.out.println("=== GYM MANAGEMENT SYSTEM ===");

        if (!login()) {
            System.out.println("Exiting...");
            return;
        }

        while (true) {
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    membershipMenu();
                    break;
                case 2:
                    trainerMenu();
                    break;
                case 3:
                    memberMenu();
                    break;
                case 4:
                    sessionMenu();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static boolean login() {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("Welcome, " + username + "! Role: " + user.getRole());
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Membership Management");
        System.out.println("2. Trainer Management");
        System.out.println("3. Member Management");
        System.out.println("4. Training Session Management");
        System.out.println("5. Exit");
        System.out.print("Enter choice: ");
    }

    private static void membershipMenu() {
        while (true) {
            System.out.println("\n--- MEMBERSHIP MENU ---");
            System.out.println("1. Add Membership");
            System.out.println("2. View All Memberships");
            System.out.println("3. Update Membership");
            System.out.println("4. Delete Membership");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter type: ");
                    String type = scanner.nextLine();
                    System.out.print("Enter category (e.g., BASIC, VIP): ");
                    String category = scanner.nextLine();
                    System.out.print("Enter duration in months: ");
                    int duration = scanner.nextInt();
                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();
                    membershipController.addMembership(type, category, duration, price);
                    break;
                case 2:
                    membershipController.displayAllMemberships();
                    break;
                case 3:
                    System.out.print("Enter membership ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new type: ");
                    type = scanner.nextLine();
                    System.out.print("Enter new category: ");
                    category = scanner.nextLine();
                    System.out.print("Enter new duration: ");
                    duration = scanner.nextInt();
                    System.out.print("Enter new price: ");
                    price = scanner.nextDouble();
                    scanner.nextLine();
                    membershipController.updateMembership(id, type, category, duration, price);
                    break;
                case 4:
                    System.out.print("Enter membership ID: ");
                    id = scanner.nextInt();
                    scanner.nextLine();
                    membershipController.deleteMembership(id);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
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
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter specialization: ");
                    String spec = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter phone: ");
                    String phone = scanner.nextLine();
                    trainerController.addTrainer(name, spec, email, phone);
                    break;
                case 2:
                    trainerController.displayAllTrainers();
                    break;
                case 3:
                    System.out.print("Enter trainer ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new name: ");
                    name = scanner.nextLine();
                    System.out.print("Enter new specialization: ");
                    spec = scanner.nextLine();
                    System.out.print("Enter new email: ");
                    email = scanner.nextLine();
                    System.out.print("Enter new phone: ");
                    phone = scanner.nextLine();
                    trainerController.updateTrainer(id, name, spec, email, phone);
                    break;
                case 4:
                    System.out.print("Enter trainer ID: ");
                    id = scanner.nextInt();
                    scanner.nextLine();
                    trainerController.deleteTrainer(id);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void memberMenu() {
        while (true) {
            System.out.println("\n--- MEMBER MENU ---");
            System.out.println("1. Register New Member");
            System.out.println("2. View All Members");
            System.out.println("3. View Active Members");
            System.out.println("4. Check Membership Validity");
            System.out.println("5. Update Member");
            System.out.println("6. Delete Member");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter phone: ");
                    String phone = scanner.nextLine();
                    System.out.print("Enter membership ID: ");
                    int mId = scanner.nextInt();
                    scanner.nextLine();
                    memberController.registerMember(name, email, phone, mId);
                    break;
                case 2:
                    memberController.displayAllMembers();
                    break;
                case 3:
                    memberController.displayActiveMembers();
                    break;
                case 4:
                    System.out.print("Enter member ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    memberController.checkMembershipValidity(id);
                    break;
                case 5:
                    System.out.print("Enter member ID: ");
                    id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new name: ");
                    name = scanner.nextLine();
                    System.out.print("Enter new email: ");
                    email = scanner.nextLine();
                    System.out.print("Enter new phone: ");
                    phone = scanner.nextLine();
                    memberController.updateMember(id, name, email, phone);
                    break;
                case 6:
                    System.out.print("Enter member ID: ");
                    id = scanner.nextInt();
                    scanner.nextLine();
                    memberController.deleteMember(id);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void sessionMenu() {
        while (true) {
            System.out.println("\n--- TRAINING SESSION MENU ---");
            System.out.println("1. Book Training Session");
            System.out.println("2. View All Sessions");
            System.out.println("3. View Member Sessions");
            System.out.println("4. View Trainer Sessions");
            System.out.println("5. Delete Session");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter member ID: ");
                    int mId = scanner.nextInt();
                    System.out.print("Enter trainer ID: ");
                    int tId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter date and time (yyyy-MM-dd HH:mm): ");
                    String dtStr = scanner.nextLine();
                    LocalDateTime dt = LocalDateTime.parse(dtStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    System.out.print("Enter duration in minutes: ");
                    int dur = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter session type: ");
                    String type = scanner.nextLine();
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    sessionController.bookSession(mId, tId, dt, dur, type, category);
                    break;
                case 2:
                    sessionController.displayAllSessions();
                    break;
                case 3:
                    System.out.print("Enter member ID: ");
                    mId = scanner.nextInt();
                    scanner.nextLine();
                    sessionController.displayMemberSessions(mId);
                    break;
                case 4:
                    System.out.print("Enter trainer ID: ");
                    tId = scanner.nextInt();
                    scanner.nextLine();
                    sessionController.displayTrainerSessions(tId);
                    break;
                case 5:
                    System.out.print("Enter session ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    sessionController.deleteSession(id);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}