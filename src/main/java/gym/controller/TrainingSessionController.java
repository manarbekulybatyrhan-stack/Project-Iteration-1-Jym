package gym.controller;

import gym.dto.FullTrainingSessionDTO;
import gym.factory.RepositoryFactory;
import gym.model.SessionCategory;
import gym.model.TrainingSession;
import gym.repository.MemberRepository;
import gym.repository.TrainerRepository;
import gym.repository.TrainingSessionRepository;
import gym.validation.CustomExceptions.InvalidDurationException;
import gym.validation.ValidationService;

import java.time.LocalDateTime;
import java.util.List;

public class TrainingSessionController {

    private final TrainingSessionRepository sessionRepository;
    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;

    public TrainingSessionController() {
        RepositoryFactory factory = RepositoryFactory.getInstance();
        this.sessionRepository = factory.createTrainingSessionRepository();
        this.memberRepository = factory.createMemberRepository();
        this.trainerRepository = factory.createTrainerRepository();
    }

    public void bookSession(int memberId, int trainerId, LocalDateTime dateTime, int duration, String type) {
        ValidationService validator = ValidationService.getInstance();

        try {
            validator.validateDuration(duration);

            int newId = sessionRepository.getNextId();

            SessionCategory category = parseCategory(type);

            TrainingSession session = new TrainingSession(
                    newId,
                    memberId,
                    trainerId,
                    dateTime,
                    duration,
                    type,
                    category
            );

            sessionRepository.add(session);
            System.out.println("✓ Session booked successfully! ID: " + newId);

        } catch (InvalidDurationException e) {
            System.out.println("✗ Validation Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Unexpected Error: " + e.getMessage());
        }
    }

    public void displaySessionDetails(int sessionId) {
        FullTrainingSessionDTO fullSession = sessionRepository.getFullSessionDescription(sessionId);

        if (fullSession != null) {
            System.out.println("\n=== COMPLETE SESSION DETAILS ===");
            System.out.println(fullSession);
            System.out.println("================================");
        } else {
            System.out.println("Session with ID " + sessionId + " not found!");
        }
    }

    public void displayAllSessions() {
        List<TrainingSession> sessions = sessionRepository.findAll();

        if (sessions.isEmpty()) {
            System.out.println("No training sessions found.");
            return;
        }

        printSessionTable(sessions, "All Training Sessions");
    }

    public void displayMemberSessions(int memberId) {
        List<TrainingSession> sessions = sessionRepository.findByMemberId(memberId);

        if (sessions.isEmpty()) {
            System.out.println("No sessions found for member ID: " + memberId);
        } else {
            printSessionTable(sessions, "Member's Training Sessions");
        }
    }

    public void displayTrainerSessions(int trainerId) {
        List<TrainingSession> sessions = sessionRepository.findByTrainerId(trainerId);

        if (sessions.isEmpty()) {
            System.out.println("No sessions found for trainer ID: " + trainerId);
        } else {
            printSessionTable(sessions, "Trainer's Training Sessions");
        }
    }

    public void deleteSession(int id) {
        TrainingSession existing = sessionRepository.findById(id);

        if (existing != null) {
            sessionRepository.delete(id);
            System.out.println("Training session " + id + " deleted successfully.");
        } else {
            System.out.println("Cannot delete: Session " + id + " not found.");
        }
    }

    private void printSessionTable(List<TrainingSession> sessions, String title) {
        System.out.println("\n" + title + ":");
        System.out.printf("%-5s | %-10s | %-10s | %-20s | %-10s | %-15s | %-12s%n",
                "ID", "Member ID", "Trainer ID", "Date", "Duration", "Type", "Category");
        System.out.println("---------------------------------------------------------------------------------------------------");

        for (TrainingSession s : sessions) {
            System.out.printf("%-5d | %-10d | %-10d | %-20s | %-10d | %-15s | %-12s%n",
                    s.getId(),
                    s.getMemberId(),
                    s.getTrainerId(),
                    s.getSessionDate(),
                    s.getDurationMinutes(),
                    s.getType(),
                    s.getCategory()
            );
        }
        System.out.println("Total: " + sessions.size());
    }

    private SessionCategory parseCategory(String type) {
        if (type == null) return SessionCategory.CARDIO;
        String t = type.trim().toUpperCase();
        try {
            return SessionCategory.valueOf(t);
        } catch (Exception e) {
            return SessionCategory.CARDIO;
        }
    }
}
