package gym.controller;

import gym.dto.FullTrainingSessionDTO;
import gym.model.TrainingSession;
import gym.repository.MemberRepository;
import gym.repository.TrainerRepository;
import gym.repository.TrainingSessionRepository;
import gym.repository.impl.MemberRepositoryImpl;
import gym.repository.impl.TrainerRepositoryImpl;
import gym.repository.impl.TrainingSessionRepositoryImpl;
import gym.validation.ValidationService;
// Импортируем конкретное исключение, если оно в отдельном файле или в классе
import gym.validation.CustomExceptions.InvalidDurationException;

import java.time.LocalDateTime;
import java.util.List;

public class TrainingSessionController {
    // Используем интерфейсы для полей (хорошая практика)
    private final TrainingSessionRepository sessionRepository;
    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;

    public TrainingSessionController() {
        this.sessionRepository = new TrainingSessionRepositoryImpl();
        this.memberRepository = new MemberRepositoryImpl();
        this.trainerRepository = new TrainerRepositoryImpl();
    }

    public void bookSession(int memberId, int trainerId, LocalDateTime dateTime,
                            int duration, String type) {
        ValidationService validator = ValidationService.getInstance();

        try {
            // 1. Сначала валидация
            validator.validateDuration(duration);

            // 2. Получаем ID
            int newId = sessionRepository.getNextId();

            // 3. Создаем объект (Здесь была ошибка!)
            // Убедись, что в TrainingSession есть конструктор для этих 6 параметров
            TrainingSession session = new TrainingSession(
                    newId,
                    memberId,
                    trainerId,
                    dateTime,
                    duration,
                    type
            );

            // 4. Сохраняем
            sessionRepository.add(session);
            System.out.println("✓ Session booked successfully! ID: " + newId);

        } catch (InvalidDurationException e) {
            System.out.println("✗ Validation Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Unexpected Error: " + e.getMessage());
        }
    }

    public void displaySessionDetails(int sessionId) {
        FullTrainingSessionDTO fullSession =
                sessionRepository.getFullSessionDescription(sessionId);

        if (fullSession != null) {
            System.out.println("\n=== COMPLETE SESSION DETAILS ===");
            System.out.println(fullSession); // toString() должен быть переопределен в DTO
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
        // Желательно сначала проверить, существует ли сессия
        if (sessionRepository.getFullSessionDescription(id) != null) {
            sessionRepository.delete(id);
            System.out.println("Training session " + id + " deleted successfully.");
        } else {
            System.out.println("Cannot delete: Session " + id + " not found.");
        }
    }

    // Вспомогательный метод, чтобы не дублировать код вывода таблицы
    private void printSessionTable(List<TrainingSession> sessions, String title) {
        System.out.println("\n" + title + ":");
        System.out.printf("%-5s | %-10s | %-10s | %-20s | %-10s | %-15s%n",
                "ID", "Member ID", "Trainer ID", "Date", "Duration", "Type");
        System.out.println("--------------------------------------------------------------------------------");

        for (TrainingSession s : sessions) {
            System.out.printf("%-5d | %-10d | %-10d | %-20s | %-10d | %-15s%n",
                    s.getId(), s.getMemberId(), s.getTrainerId(),
                    s.getSessionDate(), s.getDurationMinutes(), s.getType());
        }
        System.out.println("Total: " + sessions.size());
    }
}