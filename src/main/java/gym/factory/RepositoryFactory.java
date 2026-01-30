package gym.factory;

import gym.repository.*;

/**
 * Design Pattern: Factory
 * SOLID: Open/Closed Principle - extend without modifying
 * REQUIREMENT 2: Design Patterns
 */
public class RepositoryFactory {
    private static RepositoryFactory instance;

    private RepositoryFactory() {}

    public static RepositoryFactory getInstance() {
        if (instance == null) {
            instance = new RepositoryFactory();
        }
        return instance;
    }

    public MemberRepository createMemberRepository() {
        return new MemberRepository();
    }

    public TrainerRepository createTrainerRepository() {
        return new TrainerRepository();
    }

    public MembershipRepository createMembershipRepository() {
        return new MembershipRepository();
    }

    public TrainingSessionRepository createTrainingSessionRepository() {
        return new TrainingSessionRepository();
    }
}
