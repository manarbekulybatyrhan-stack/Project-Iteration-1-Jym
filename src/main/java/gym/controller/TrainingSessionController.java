package gym.controller;

import gym.model.Member;
import gym.model.Trainer;
import gym.model.TrainingSession;
import gym.model.SessionCategory;
import gym.repository.MemberRepository;
import gym.repository.TrainerRepository;
import gym.repository.TrainingSessionRepository;
import gym.repository.impl.MemberRepositoryImpl;
import gym.repository.impl.TrainerRepositoryImpl;
import gym.repository.impl.TrainingSessionRepositoryImpl;
import gym.validation.ValidationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TrainingSessionController {
    private TrainingSessionRepository sessionRepository = new TrainingSessionRepositoryImpl();
    private MemberRepository memberRepository = new MemberRepositoryImpl();
    private TrainerRepository trainerRepository = new TrainerRepositoryImpl();

    public void bookSession(int memberId, int trainerId, LocalDateTime sessionDate, int durationMinutes, String type, String category) {
        try {
            ValidationService.getInstance().validateDuration(durationMinutes);

            Member member = memberRepository.findById(memberId);
            Trainer trainer = trainerRepository.findById(trainerId);

            if (member == null || trainer == null) {
                System.out.println("Member or Trainer not found.");
                return;
            }

            if (member.getExpiryDate().isBefore(LocalDate.now())) {
                System.out.println("Membership expired. Cannot book session.");
                return;
            }

            TrainingSession session = new TrainingSession();
            session.setMemberId(memberId);
            session.setTrainerId(trainerId);
            session.setSessionDate(sessionDate);
            session.setDurationMinutes(durationMinutes);
            session.setCategory(gym.model.SessionCategory.valueOf(category.toUpperCase()));
            session.setCategory(SessionCategory.valueOf(category.toUpperCase()));

            sessionRepository.create(session);
            System.out.println("Session booked with ID: " + session.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid category name.");
        } catch (Exception e) {
            System.out.println("Validation Error: " + e.getMessage());
        }
    }

    public void displayAllSessions() {
        List<TrainingSession> sessions = sessionRepository.findAll();
        if (sessions.isEmpty()) {
            System.out.println("No sessions found.");
            return;
        }
        System.out.println("\nTraining Sessions:");
        System.out.println("ID | Member ID | Trainer ID | Date | Duration | Type | Category");
        System.out.println("----------------------------------------------------------------");
        for (TrainingSession s : sessions) {
            System.out.println(s.getId() + " | " + s.getMemberId() + " | " +
                    s.getTrainerId() + " | " + s.getSessionDate() + " | " +
                    s.getDurationMinutes() + " min | " + s.getType() + " | " + s.getCategory());
        }
    }

    public void displayMemberSessions(int memberId) {
        List<TrainingSession> sessions = sessionRepository.findByMemberId(memberId);
        if (sessions.isEmpty()) {
            System.out.println("No sessions found for this member.");
            return;
        }
        for (TrainingSession s : sessions) {
            System.out.println(s.getId() + " | " + s.getTrainerId() + " | " + s.getSessionDate() + " | " + s.getType() + " | " + s.getCategory());
        }
    }

    public void displayTrainerSessions(int trainerId) {
        List<TrainingSession> sessions = sessionRepository.findByTrainerId(trainerId);
        if (sessions.isEmpty()) {
            System.out.println("No sessions found for this trainer.");
            return;
        }
        for (TrainingSession s : sessions) {
            System.out.println(s.getId() + " | " + s.getMemberId() + " | " + s.getSessionDate() + " | " + s.getType() + " | " + s.getCategory());
        }
    }

    public void deleteSession(int id) {
        sessionRepository.delete(id);
        System.out.println("Session deleted successfully.");
    }
}