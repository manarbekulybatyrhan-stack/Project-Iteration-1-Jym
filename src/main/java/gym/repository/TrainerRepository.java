package gym.repository;

import gym.model.Trainer;

import java.util.List;

public interface TrainerRepository {
    void create(Trainer trainer);

    Trainer findById(int id);

    List<Trainer> findAll();

    void update(Trainer trainer);

    void delete(int id);
}
