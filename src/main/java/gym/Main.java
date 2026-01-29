package gym;

import gym.controller.MemberController;
import gym.controller.MembershipController;
import gym.controller.TrainerController;
import gym.controller.TrainingSessionController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import gym.security.User;
import gym.security.Role;

public class Main {
    private static MembershipController membershipController = new MembershipController();
    private static TrainerController trainerController = new TrainerController();
    private static MemberController memberController = new MemberController();
    private static TrainingSessionController sessionController = new TrainingSessionController();
    private static Scanner scanner = new Scanner(System.in);

    private static User currentUser = null;

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        System.out.println("Select role:");
        System.out.println("1. ADMIN");
        System.out.println("2. MANAGER");
        System.out.println("3. TRAINER");
        System.out.println("4. MEMBER");
        int roleChoice = scanner.nextInt();
        scanner.nextLine();
        
        Role role = switch (roleChoice) {
            case 1 -> Role.ADMIN;
            case 2 -> Role.MANAGER;
            case 3 -> Role.TRAINER;
            default -> Role.MEMBER;
        };
        
        currentUser = new User(1, username, "password", role);
        System.out.println("✓ Logged in as " + username + " (" + role + ")");
    }

    // Add method to check authorization:
    private static boolean hasPermission(String action) {
        if (currentUser == null) {
            System.out.println("✗ Not logged in!");
            return false;
        }
        if (!currentUser.hasPermission(action)) {
            System.out.println("✗ Unauthorized: " + action);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("=== GYM MANAGEMENT SYSTEM ===");
        login();
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
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
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
                    System.out.print("Enter type (Basic/Premium/VIP): ");
                    String type = scanner.nextLine();

                    System.out.print("Enter duration in months: ");
                    int duration = scanner.nextInt();

                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();

                    membershipController.addMembership(type, duration, price);

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

                    System.out.print("Enter new duration: ");
                    duration = scanner.nextInt();

                    System.out.print("Enter new price: ");
                    price = scanner.nextDouble();
                    scanner.nextLine();

                    membershipController.updateMembership(id, type, duration, price);
                    
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
                    int membershipId = scanner.nextInt();
                    scanner.nextLine();

                    memberController.registerMember(name, email, phone, membershipId);

                    break;

                case 2:
                    memberController.displayAllMembers();

                    break;
                case 3:
                    memberController.displayActiveMembers();

                    break;
                case 4:
                    System.out.print("Enter member ID: ");
                    int memberId = scanner.nextInt();
                    scanner.nextLine();

                    memberController.checkMembershipValidity(memberId);

                    break;
                case 5:
                    System.out.print("Enter member ID: ");
                    int id = scanner.nextInt();
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
                    int memberId = scanner.nextInt();
                    
                    System.out.print("Enter trainer ID: ");
                    int trainerId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter session date and time (yyyy-MM-dd HH:mm): ");
                    String dateTimeStr = scanner.nextLine();

                    LocalDateTime dateTime = LocalDateTime.parse(
                        dateTimeStr, 
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    );

                    System.out.print("Enter duration in minutes: ");
                    int duration = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter session type (Cardio/Strength/Yoga/etc): ");
                    String type = scanner.nextLine();

                    sessionController.bookSession(memberId, trainerId, dateTime, duration, type);

                    break;

                case 2:
                    sessionController.displayAllSessions();
                    break;
                case 3:
                    System.out.print("Enter member ID: ");
                    memberId = scanner.nextInt();
                    scanner.nextLine();
                    sessionController.displayMemberSessions(memberId);

                    break;

                case 4:
                    System.out.print("Enter trainer ID: ");
                    trainerId = scanner.nextInt();
                    scanner.nextLine();

                    sessionController.displayTrainerSessions(trainerId);
                    
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
