package gym.repository;

import gym.model.Member;

import java.util.List;

public interface MemberRepository {
    void add(Member member);

    int getNextId();

    void create(Member member);

    Member findById(int id);

    List<Member> findAll();

    void update(Member member);

    void delete(int id);

    List<Member> findActiveMembers();

    List<Member> getValidatedMembers() throws Exception;

    List<Member> getActiveMembers();

    Member findByEmail(String email);
}
