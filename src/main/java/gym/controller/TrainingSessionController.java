package gym.controller;

import gym.model.Member;
import gym.model.Trainer;
import gym.model.TrainingSession;
import gym.repository.MemberRepository;
import gym.repository.TrainerRepository;
import gym.repository.TrainingSessionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import gym.validation.ValidationService;
import gym.dto.FullTrainingSessionDTO;
import gym.validation.*;

import gym.validation.CustomExceptions.*;

public class TrainingSessionController {
    private TrainingSessionRepository sessionRepository;
    private MemberRepository memberRepository;
    private TrainerRepository trainerRepository;

    public TrainingSessionController() {
        this.sessionRepository = new TrainingSessionRepository();
        this.memberRepository = new MemberRepository();
        this.trainerRepository = new TrainerRepository();
    }

    public void bookSession(int memberId, int trainerId, LocalDateTime dateTime, 
                       int duration, String type) {
        ValidationService validator = ValidationService.getInstance();
        
        try {
            // Validate duration
            validator.validateDuration(duration);
            
            TrainingSession session = new TrainingSession(
                sessionRepository.getNextId(), memberId, trainerId, dateTime, duration, type
            );
            sessionRepository.add(session);
            System.out.println("✓ Session booked successfully!");
            
        } catch (InvalidDurationException e) {
            System.out.println("✗ Validation Error: " + e.getMessage());
        }
    }

    public void displaySessionDetails(int sessionId) {
        FullTrainingSessionDTO fullSession = 
            sessionRepository.getFullSessionDescription(sessionId);
        
        if (fullSession != null) {
            System.out.println("\n=== COMPLETE SESSION DETAILS ===");
            System.out.println(fullSession);
            System.out.println("================================");
        } else {
            System.out.println("Session not found!");
        }
    }

    public void displayAllSessions() {
        List<TrainingSession> sessions = sessionRepository.findAll();

        if (sessions.isEmpty()) {
            System.out.println("No training sessions found.");

            return;
        }

        System.out.println("\nTraining Sessions:");
        System.out.println("ID | Member ID | Trainer ID | Date | Duration | Type");
        System.out.println("------------------------------------------------");

        for (TrainingSession s : sessions) {
            System.out.println(s.getId() + " | " + s.getMemberId() + " | " + 
                s.getTrainerId() + " | " + s.getSessionDate() + " | " + 
                s.getDurationMinutes() + " min | " + s.getType());
        }
    }


    public void displayMemberSessions(int memberId) {
        List<TrainingSession> sessions = sessionRepository.findByMemberId(memberId);

        if (sessions.isEmpty()) {
            System.out.println("No sessions found for this member.");

            return;
        }

        System.out.println("\nMember's Training Sessions:");
        System.out.println("ID | Trainer ID | Date | Duration | Type");
        System.out.println("------------------------------------------------");

        for (TrainingSession s : sessions) {
            System.out.println(s.getId() + " | " + s.getTrainerId() + " | " + 
                s.getSessionDate() + " | " + s.getDurationMinutes() + " min | " + s.getType());
        }

        System.out.println("\nTotal sessions: " + sessions.size());
    }


    public void displayTrainerSessions(int trainerId) {
        List<TrainingSession> sessions = sessionRepository.findByTrainerId(trainerId);

        if (sessions.isEmpty()) {
            System.out.println("No sessions found for this trainer.");

            return;
        }

        System.out.println("\nTrainer's Training Sessions:");
        System.out.println("ID | Member ID | Date | Duration | Type");
        System.out.println("------------------------------------------------");

        for (TrainingSession s : sessions) {
            System.out.println(s.getId() + " | " + s.getMemberId() + " | " + 
                s.getSessionDate() + " | " + s.getDurationMinutes() + " min | " + s.getType());
        }

        System.out.println("\nTotal sessions: " + sessions.size());
    }


    public void deleteSession(int id) {
        sessionRepository.delete(id);

        System.out.println("Training session deleted successfully.");
    }
}