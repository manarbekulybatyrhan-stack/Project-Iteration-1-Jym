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

public class TrainingSessionController {
    private TrainingSessionRepository sessionRepository;
    private MemberRepository memberRepository;
    private TrainerRepository trainerRepository;

    public TrainingSessionController() {
        this.sessionRepository = new TrainingSessionRepository();
        this.memberRepository = new MemberRepository();
        this.trainerRepository = new TrainerRepository();
    }

    public void bookSession(int memberId, int trainerId, LocalDateTime sessionDate, int durationMinutes, String type) {
        Member member = memberRepository.findById(memberId);

        Trainer trainer = trainerRepository.findById(trainerId);

        if (member == null) {
            System.out.println("Member not found.");

            return;
        }

        if (trainer == null) {
            System.out.println("Trainer not found.");
            
            return;
        }

        if (member.getExpiryDate().isBefore(LocalDate.now())) {
            System.out.println("Member's membership has expired. Cannot book session.");

            return;
        }

        TrainingSession session = new TrainingSession();

        session.setMemberId(memberId);
        session.setTrainerId(trainerId);
        session.setSessionDate(sessionDate);
        session.setDurationMinutes(durationMinutes);
        session.setType(type);

        sessionRepository.create(session);

        System.out.println("Training session booked with ID: " + session.getId());
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