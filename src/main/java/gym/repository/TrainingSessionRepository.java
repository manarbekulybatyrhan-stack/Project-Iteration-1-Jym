package gym.repository;

import gym.dto.FullTrainingSessionDTO;
import gym.model.TrainingSession;

import java.util.List;

public interface TrainingSessionRepository {
    void add(TrainingSession session);

    int getNextId();

    void create(TrainingSession session);

    TrainingSession findById(int id);

    List<TrainingSession> findAll();

    void update(TrainingSession session);

    void delete(int id);

    List<TrainingSession> findByMemberId(int memberId);

    List<TrainingSession> findByTrainerId(int trainerId);

    FullTrainingSessionDTO getFullSessionDescription(int sessionId);

    // Lambda: Get sessions by type with filter
    List<TrainingSession> getSessionsByType(String type);
}
