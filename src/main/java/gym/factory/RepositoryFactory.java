package gym.factory;

import gym.repository.MemberRepository;
import gym.repository.MembershipRepository;
import gym.repository.TrainerRepository;
import gym.repository.TrainingSessionRepository;

import gym.repository.impl.MemberRepositoryImpl;
import gym.repository.impl.MembershipRepositoryImpl;
import gym.repository.impl.TrainerRepositoryImpl;
import gym.repository.impl.TrainingSessionRepositoryImpl;

public class RepositoryFactory {

    private static final RepositoryFactory instance = new RepositoryFactory();

    private RepositoryFactory() {
    }

    public static RepositoryFactory getInstance() {
        return instance;
    }

    public MemberRepository createMemberRepository() {
        return new MemberRepositoryImpl();
    }

    public TrainerRepository createTrainerRepository() {
        return new TrainerRepositoryImpl();
    }

    public MembershipRepository createMembershipRepository() {
        return new MembershipRepositoryImpl();
    }

    public TrainingSessionRepository createTrainingSessionRepository() {
        return new TrainingSessionRepositoryImpl();
    }
}

