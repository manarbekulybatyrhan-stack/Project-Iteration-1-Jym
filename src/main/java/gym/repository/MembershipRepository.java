package gym.repository;

import gym.model.Membership;

import java.util.List;

public interface MembershipRepository {
    void create(Membership membership);

    Membership findById(int id);

    List<Membership> findAll();

    void update(Membership membership);

    void delete(int id);
}
